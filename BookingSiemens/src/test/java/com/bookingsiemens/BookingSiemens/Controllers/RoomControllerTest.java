package com.bookingsiemens.BookingSiemens.Controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.bookingsiemens.BookingSiemens.Commons.Hotel;
import com.bookingsiemens.BookingSiemens.Commons.Room;
import com.bookingsiemens.BookingSiemens.CustomExceptions.DuplicatedInstanceException;
import com.bookingsiemens.BookingSiemens.CustomExceptions.FieldNullException;
import com.bookingsiemens.BookingSiemens.CustomExceptions.InstanceNotFoundException;
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
public class RoomControllerTest {

  @Mock
  private RoomService roomService;
  private RoomController roomController;

  @BeforeEach
  void setUp() {
    roomService = Mockito.mock(RoomService.class);
    roomController = new RoomController(roomService);
  }

  @Test
  void createRoomFieldNullExceptionTest() {
    Room room = new Room(new Hotel(), 2, 3, 4);
    when(roomService.createRoom(room)).thenThrow(FieldNullException.class);
    ResponseEntity<Room> r = roomController.createRoom(room);
    assertEquals(HttpStatus.BAD_REQUEST, r.getStatusCode());
  }

  @Test
  void createRoomDuplicatedInstanceExceptionTest() {
    Room room = new Room(new Hotel(), 2, 3, 4);
    when(roomService.createRoom(room)).thenThrow(DuplicatedInstanceException.class);
    ResponseEntity<Room> r = roomController.createRoom(room);
    assertEquals(HttpStatus.FORBIDDEN, r.getStatusCode());
  }

  @Test
  void createRoomSuccessTest() {
    Room room = new Room(new Hotel(), 2, 3, 4);
    when(roomService.createRoom(room)).thenReturn(room);
    ResponseEntity<Room> r = roomController.createRoom(room);
    assertEquals(HttpStatus.OK, r.getStatusCode());
    assertEquals(room, r.getBody());
  }

  @Test
  void deleteRoomFieldNullExceptionTest() {
    doThrow(FieldNullException.class).when(roomService).deleteRoom(any());
    ResponseEntity<String> r = roomController.deleteRoom(UUID.randomUUID());
    assertEquals(HttpStatus.BAD_REQUEST, r.getStatusCode());
  }

  @Test
  void deleteRoomInstanceNotFoundExceptionTest() {
    UUID id = UUID.randomUUID();
    doThrow(InstanceNotFoundException.class).when(roomService).deleteRoom(id);
    ResponseEntity<String> r = roomController.deleteRoom(id);
    assertEquals(HttpStatus.NOT_FOUND, r.getStatusCode());
  }

  @Test
  void deleteRoomSuccessTest() {
    UUID id = UUID.randomUUID();
    doNothing().when(roomService).deleteRoom(id);
    ResponseEntity<String> r = roomController.deleteRoom(id);
    assertEquals(HttpStatus.OK, r.getStatusCode());
    assertEquals("Success.", r.getBody());
  }
}
