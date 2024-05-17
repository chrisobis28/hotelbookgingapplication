package com.bookingsiemens.BookingSiemens.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.bookingsiemens.BookingSiemens.Commons.Hotel;
import com.bookingsiemens.BookingSiemens.Commons.Location;
import com.bookingsiemens.BookingSiemens.Commons.Room;
import com.bookingsiemens.BookingSiemens.CustomExceptions.FieldNullException;
import com.bookingsiemens.BookingSiemens.CustomExceptions.InstanceNotFoundException;
import com.bookingsiemens.BookingSiemens.Repositories.HotelRepository;
import java.util.ArrayList;
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
public class HotelServiceTest {

  @Mock
  private HotelRepository hotelRepository;
  private HotelService hotelService;

  @BeforeEach
  void setUp() {
    hotelRepository = Mockito.mock(HotelRepository.class);
    hotelService = new HotelService(hotelRepository);
  }

  @Test
  void addHotelsTestNull() {
    Hotel h1 = new Hotel("this", new Location(3, 4));
    Hotel h2 = new Hotel(null, new Location(3, 4));
    assertThrows(FieldNullException.class, () -> hotelService.addHotels(List.of(h1, h2)));
  }

  @Test
  void addHotelsTestSuccess() {
    Hotel h1 = new Hotel("this", new Location(3, 4));
    Hotel h2 = new Hotel("that", new Location(3, 4));
    Hotel h3 = new Hotel("those", new Location(3, 4));
    when(hotelRepository.saveAll(any())).thenReturn(List.of(h1, h2, h3));
    assertEquals(List.of(h1, h2, h3), hotelService.addHotels(List.of(h1, h2, h3)));
  }

  @Test
  void getHotelRoomsTestIdNull() {
    assertThrows(FieldNullException.class, () -> hotelService.getHotelRooms(null));
  }

  @Test
  void getHotelRoomsTestNotFound() {
    UUID id = UUID.randomUUID();
    when(hotelRepository.findById(id)).thenReturn(Optional.empty());
    assertThrows(InstanceNotFoundException.class, () -> hotelService.getHotelRooms(id));
  }

  @Test
  void getHotelRoomsTestSuccess() {
    UUID id = UUID.randomUUID();
    Hotel h = new Hotel("name", new Location(3, 4));
    h.setHotel_id(id);
    List<Room> list = List.of(new Room(), new Room(), new Room());
    h.setRooms(list);
    when(hotelRepository.findById(id)).thenReturn(Optional.of(h));
    assertEquals(list, hotelService.getHotelRooms(id));
  }

  @Test
  void deleteHotelTestNull() {
    assertThrows(FieldNullException.class, () -> hotelService.deleteHotel(null));
  }

  @Test
  void deleteHotelTestNotFound() {
    UUID id = UUID.randomUUID();
    when(hotelRepository.findById(id)).thenReturn(Optional.empty());
    assertThrows(InstanceNotFoundException.class, () -> hotelService.deleteHotel(id));
  }

  @Test
  void deleteHotelTestSuccess() {
    UUID id = UUID.randomUUID();
    Hotel h = new Hotel("name", new Location(3, 4));
    when(hotelRepository.findById(id)).thenReturn(Optional.of(h));
    doNothing().when(hotelRepository).deleteById(any());
    hotelService.deleteHotel(id);
  }

  @Test
  void getHotelsNearby() {
    Hotel h1 = new Hotel("name", new Location(3, 4));
    Hotel h2 = new Hotel("name", new Location(3, 5));
    Hotel h3 = new Hotel("name", new Location(3, 6));
    when(hotelRepository.findAll()).thenReturn(List.of(h1, h2, h3));
    assertEquals(List.of(h1, h2, h3), hotelService.getAllHotelsNearby(3, 4, 1000));
  }
}
