package com.bookingsiemens.BookingSiemens.Commons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class HotelTest {

  @Test
  void testHotelConstructorNameLocation() {
    Hotel hotel = new Hotel("Ramada", new Location(10, 10));
    assertEquals(hotel.getHotel_name(), "Ramada");
    assertEquals(hotel.getHotel_location().getLatitude(), 10);
    assertEquals(hotel.getHotel_location().getLongitude(), 10);
    assertNotNull(hotel.getFeedbacks());
    assertNotNull(hotel.getRooms());
  }

  @Test
  void testHotelConstructorNameLocationRooms() {
    Room room = new Room();
    Hotel hotel = new Hotel("Ramada", new Location(10, 10), List.of(room));
    assertEquals(hotel.getHotel_name(), "Ramada");
    assertEquals(hotel.getHotel_location().getLatitude(), 10);
    assertEquals(hotel.getHotel_location().getLongitude(), 10);
    assertNotNull(hotel.getFeedbacks());
    List<Room> rooms = new ArrayList<>();
    rooms.add(room);
    assertEquals(rooms, hotel.getRooms());
  }

  @Test
  void testHotelConstructorNameLocationRoomsFeedbacks() {
    Room room = new Room();
    Feedback feedback = new Feedback();
    Hotel hotel = new Hotel("Ramada", new Location(10, 10), List.of(room), List.of(feedback));
    assertEquals(hotel.getHotel_name(), "Ramada");
    assertEquals(hotel.getHotel_location().getLatitude(), 10);
    assertEquals(hotel.getHotel_location().getLongitude(), 10);
    assertEquals(List.of(feedback), hotel.getFeedbacks());
    assertEquals(List.of(room), hotel.getRooms());
  }
}
