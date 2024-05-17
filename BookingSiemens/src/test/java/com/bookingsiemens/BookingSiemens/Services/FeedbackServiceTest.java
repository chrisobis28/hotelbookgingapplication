package com.bookingsiemens.BookingSiemens.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bookingsiemens.BookingSiemens.Commons.Feedback;
import com.bookingsiemens.BookingSiemens.Commons.Hotel;
import com.bookingsiemens.BookingSiemens.Commons.Location;
import com.bookingsiemens.BookingSiemens.Commons.Room;
import com.bookingsiemens.BookingSiemens.CustomExceptions.FieldNullException;
import com.bookingsiemens.BookingSiemens.CustomExceptions.InstanceNotFoundException;
import com.bookingsiemens.BookingSiemens.Repositories.FeedbackRepository;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
public class FeedbackServiceTest {

  @Mock
  private FeedbackRepository feedbackRepository;
  private FeedbackService feedbackService;

  @BeforeEach
  void setUp() {
    feedbackRepository = Mockito.mock(FeedbackRepository.class);
    feedbackService = new FeedbackService(feedbackRepository);
  }

  @Test
  void createFeedbackTestNull() {
    Feedback feedback = new Feedback(null, 2, "comment");
    assertThrows(FieldNullException.class, () -> feedbackService.createFeedback(feedback));
  }

  @Test
  void createFeedbackTestSuccess() {
    Hotel h1 = new Hotel("hotel_name", new Location(2, 3));
    Feedback feedback = new Feedback(h1, 2, "comment");
    when(feedbackRepository.save(feedback)).thenReturn(feedback);
    assertEquals(feedback, feedbackService.createFeedback(feedback));
  }

  @Test
  void deleteFeedbackTestNull() {
    assertThrows(FieldNullException.class, () -> feedbackService.deleteFeedback(null));
  }

  @Test
  void deleteFeedbackTestNotFound() {
    Hotel h1 = new Hotel("hotel_name", new Location(2, 3));
    Feedback feedback = new Feedback(h1, 2, "comment");
    feedback.setFeedback_id(UUID.randomUUID());
    when(feedbackRepository.findById(feedback.getFeedback_id())).thenReturn(Optional.empty());
    assertThrows(InstanceNotFoundException.class, () -> feedbackService.deleteFeedback(feedback.getFeedback_id()));
  }

  @Test
  void deleteFeedbackTestSuccess() {
    Hotel h1 = new Hotel("hotel_name", new Location(2, 3));
    Feedback feedback = new Feedback(h1, 2, "comment");
    feedback.setFeedback_id(UUID.randomUUID());
    when(feedbackRepository.findById(feedback.getFeedback_id())).thenReturn(Optional.of(feedback));
    doNothing().when(feedbackRepository).deleteById(feedback.getFeedback_id());
    feedbackService.deleteFeedback(feedback.getFeedback_id());
    verify(feedbackRepository, times(1)).deleteById(feedback.getFeedback_id());
  }
}
