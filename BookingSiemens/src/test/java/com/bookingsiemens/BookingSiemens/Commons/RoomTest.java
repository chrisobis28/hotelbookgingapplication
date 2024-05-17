package com.bookingsiemens.BookingSiemens.Commons;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

public class RoomTest {

  @Test
  void testRoomConstructorNormal() {
    Hotel hotel = new Hotel();
    Room room = new Room(hotel, 200, 2, 300);
    assertEquals(hotel, room.getHotel());
    assertEquals(200, room.getRoom_number());
    assertEquals(2, room.getType());
    assertEquals(300, room.getPrice());
  }

  @Test
  void testRoomConstructorReservations() {
    Hotel hotel = new Hotel();
    Reservation reservation = new Reservation();
    Room room = new Room(hotel, 200, 2, 300, List.of(reservation));
    assertEquals(hotel, room.getHotel());
    assertEquals(200, room.getRoom_number());
    assertEquals(2, room.getType());
    assertEquals(300, room.getPrice());
    assertEquals(List.of(reservation), room.getReservations());
  }
}
