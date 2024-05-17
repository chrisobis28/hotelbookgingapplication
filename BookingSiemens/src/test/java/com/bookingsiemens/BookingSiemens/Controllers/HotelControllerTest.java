package com.bookingsiemens.BookingSiemens.Controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.bookingsiemens.BookingSiemens.Commons.Hotel;
import com.bookingsiemens.BookingSiemens.Commons.Room;
import com.bookingsiemens.BookingSiemens.CustomExceptions.FieldNullException;
import com.bookingsiemens.BookingSiemens.CustomExceptions.InstanceNotFoundException;
import com.bookingsiemens.BookingSiemens.Services.GeolocationService;
import com.bookingsiemens.BookingSiemens.Services.HotelService;
import com.bookingsiemens.BookingSiemens.Services.ReservationService;
import java.util.List;
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
public class HotelControllerTest {

  @Mock
  private HotelService hotelService;

  @Mock
  private GeolocationService geolocationService;

  private HotelController hotelController;

  @BeforeEach
  void setUp() {
    hotelService = Mockito.mock(HotelService.class);
    geolocationService = Mockito.mock(GeolocationService.class);
    hotelController = new HotelController(hotelService, geolocationService);
  }

  @Test
  void addHotelsTestNull() {
    when(hotelService.addHotels(any())).thenThrow(FieldNullException.class);
    ResponseEntity<List<Hotel>> re = hotelController.addHotels(List.of(new Hotel(), new Hotel()));
    assertEquals(HttpStatus.BAD_REQUEST, re.getStatusCode());
    assertNull(re.getBody());
  }

  @Test
  void addHotelsTestSuccess() {
    List<Hotel> list = List.of(new Hotel(), new Hotel());
    when(hotelService.addHotels(list)).thenReturn(list);
    ResponseEntity<List<Hotel>> re = hotelController.addHotels(list);
    assertEquals(HttpStatus.OK, re.getStatusCode());
    assertEquals(list, re.getBody());
  }

  @Test
  void deleteHotelTestNull() {
    doThrow(FieldNullException.class).when(hotelService).deleteHotel(null);
    ResponseEntity<String> re = hotelController.deleteHotel(null);
    assertEquals(HttpStatus.BAD_REQUEST, re.getStatusCode());
  }

  @Test
  void deleteHotelTestNotFound() {
    UUID id = UUID.randomUUID();
    doThrow(InstanceNotFoundException.class).when(hotelService).deleteHotel(id);
    ResponseEntity<String> re = hotelController.deleteHotel(id);
    assertEquals(HttpStatus.NOT_FOUND, re.getStatusCode());
  }

  @Test
  void deleteHotelTestSuccess() {
    UUID id = UUID.randomUUID();
    doNothing().when(hotelService).deleteHotel(id);
    ResponseEntity<String> re = hotelController.deleteHotel(id);
    assertEquals(HttpStatus.OK, re.getStatusCode());
    assertEquals("Success", re.getBody());
  }

  @Test
  void getHotelRoomsTestNull() {
    doThrow(FieldNullException.class).when(hotelService).getHotelRooms(null);
    ResponseEntity<List<Room>> re = hotelController.getHotelRooms(null);
    assertEquals(HttpStatus.BAD_REQUEST, re.getStatusCode());
  }

  @Test
  void getHotelRoomsTestNotFound() {
    UUID id = UUID.randomUUID();
    doThrow(InstanceNotFoundException.class).when(hotelService).getHotelRooms(id);
    ResponseEntity<List<Room>> re = hotelController.getHotelRooms(id);
    assertEquals(HttpStatus.NOT_FOUND, re.getStatusCode());
  }

  @Test
  void getHotelRoomsTestSuccess() {
    UUID id = UUID.randomUUID();
    List<Room> list = List.of(new Room(), new Room(), new Room());
    when(hotelService.getHotelRooms(id)).thenReturn(list);
    ResponseEntity<List<Room>> re = hotelController.getHotelRooms(id);
    assertEquals(HttpStatus.OK, re.getStatusCode());
    assertEquals(list, re.getBody());
  }
}
