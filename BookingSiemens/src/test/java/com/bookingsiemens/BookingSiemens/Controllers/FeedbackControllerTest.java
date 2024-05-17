package com.bookingsiemens.BookingSiemens.Controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.bookingsiemens.BookingSiemens.Commons.Feedback;
import com.bookingsiemens.BookingSiemens.Commons.Hotel;
import com.bookingsiemens.BookingSiemens.Commons.Room;
import com.bookingsiemens.BookingSiemens.CustomExceptions.DuplicatedInstanceException;
import com.bookingsiemens.BookingSiemens.CustomExceptions.FieldNullException;
import com.bookingsiemens.BookingSiemens.CustomExceptions.InstanceNotFoundException;
import com.bookingsiemens.BookingSiemens.Services.FeedbackService;
import com.bookingsiemens.BookingSiemens.Services.FeedbackServiceTest;
import com.bookingsiemens.BookingSiemens.Services.RoomService;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
public class FeedbackControllerTest {

  @Mock
  private FeedbackService feedbackService;
  private FeedbackController feedbackController;

  @BeforeEach
  void setUp() {
    feedbackService = Mockito.mock(FeedbackService.class);
    feedbackController = new FeedbackController(feedbackService);
  }

  @Test
  void createFeedbackFieldNullExceptionTest() {
    Feedback feedback = new Feedback(new Hotel(), 2, null);
    when(feedbackService.createFeedback(feedback)).thenThrow(FieldNullException.class);
    ResponseEntity<Feedback> r = feedbackController.addFeedback(feedback);
    assertEquals(HttpStatus.BAD_REQUEST, r.getStatusCode());
  }

  @Test
  void createRoomSuccessTest() {
    Feedback feedback = new Feedback(new Hotel(), 2, "comment");
    when(feedbackService.createFeedback(feedback)).thenReturn(feedback);
    ResponseEntity<Feedback> r = feedbackController.addFeedback(feedback);
    assertEquals(HttpStatus.OK, r.getStatusCode());
    assertEquals(feedback, r.getBody());
  }

  @Test
  void deleteRoomFieldNullExceptionTest() {
    doThrow(FieldNullException.class).when(feedbackService).deleteFeedback(any());
    ResponseEntity<String> r = feedbackController.deleteFeedback(UUID.randomUUID());
    assertEquals(HttpStatus.BAD_REQUEST, r.getStatusCode());
  }

  @Test
  void deleteRoomInstanceNotFoundExceptionTest() {
    UUID id = UUID.randomUUID();
    doThrow(InstanceNotFoundException.class).when(feedbackService).deleteFeedback(id);
    ResponseEntity<String> r = feedbackController.deleteFeedback(id);
    assertEquals(HttpStatus.NOT_FOUND, r.getStatusCode());
  }

  @Test
  void deleteRoomSuccessTest() {
    UUID id = UUID.randomUUID();
    doNothing().when(feedbackService).deleteFeedback(id);
    ResponseEntity<String> r = feedbackController.deleteFeedback(id);
    assertEquals(HttpStatus.OK, r.getStatusCode());
    assertEquals("Success.", r.getBody());
  }
}
