package com.bookingsiemens.BookingSiemens.Commons;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class LocationTest {

  @Test
  void testLocationConstructor() {
    Location location = new Location(10, 10);
    assertEquals(10, location.getLatitude());
    assertEquals(10, location.getLongitude());
  }
}
