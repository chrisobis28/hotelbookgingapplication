package com.bookingsiemens.BookingSiemens.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bookingsiemens.BookingSiemens.Commons.Hotel;
import com.bookingsiemens.BookingSiemens.Commons.Location;
import com.bookingsiemens.BookingSiemens.Commons.Room;
import com.bookingsiemens.BookingSiemens.CustomExceptions.DuplicatedInstanceException;
import com.bookingsiemens.BookingSiemens.CustomExceptions.FieldNullException;
import com.bookingsiemens.BookingSiemens.CustomExceptions.InstanceNotFoundException;
import com.bookingsiemens.BookingSiemens.Repositories.RoomRepository;
import java.util.List;
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
public class RoomServiceTest {

  @Mock
  private RoomRepository roomRepository;
  private RoomService roomService;

  @BeforeEach
  void setUp() {
    roomRepository = Mockito.mock(RoomRepository.class);
    roomService = new RoomService(roomRepository);
  }

  @Test
  void createRoomTestNull() {
    Room room = new Room(null, 2, 3, 4);
    assertThrows(FieldNullException.class, () -> roomService.createRoom(room));
  }

  @Test
  void createRoomTestDuplicate() {
    Hotel h1 = new Hotel("hotel_name", new Location(2, 3));
    Room room = new Room(h1, 2, 3, 4);
    when(roomRepository.findAll()).thenReturn(List.of(room));
    assertThrows(DuplicatedInstanceException.class, () -> roomService.createRoom(room));
  }

  @Test
  void createRoomTestSuccess() {
    Hotel h1 = new Hotel("hotel_name", new Location(2, 3));
    Room room = new Room(h1, 2, 3, 4);
    when(roomRepository.findAll()).thenReturn(List.of());
    when(roomRepository.save(room)).thenReturn(room);
    assertEquals(room, roomService.createRoom(room));
  }

  @Test
  void deleteRoomTestNull() {
    assertThrows(FieldNullException.class, () -> roomService.deleteRoom(null));
  }

  @Test
  void deleteRoomTestNoRoomFound() {
    Hotel h1 = new Hotel("hotel_name", new Location(2, 3));
    Room room = new Room(h1, 2, 3, 4);
    room.setRoom_id(UUID.randomUUID());
    when(roomRepository.findById(room.getRoom_id())).thenReturn(Optional.empty());
    assertThrows(InstanceNotFoundException.class, () -> roomService.deleteRoom(room.getRoom_id()));
  }

  @Test
  void deleteRoomTestSuccess() {
    Hotel h1 = new Hotel("hotel_name", new Location(2, 3));
    Room room = new Room(h1, 2, 3, 4);
    UUID id = UUID.randomUUID();
    room.setRoom_id(id);
    when(roomRepository.findById(id)).thenReturn(Optional.of(room));
    roomService.deleteRoom(id);
    verify(roomRepository, times(1)).deleteById(id);
  }
}
