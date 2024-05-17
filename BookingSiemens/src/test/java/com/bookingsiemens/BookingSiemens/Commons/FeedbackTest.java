package com.bookingsiemens.BookingSiemens.Commons;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class FeedbackTest {

  @Test
  void testFeedbackConstructor() {
    Hotel hotel = new Hotel();
    Feedback feedback = new Feedback(hotel, 3, "Nice.");
    assertEquals(3, feedback.getStars());
    assertEquals("Nice.", feedback.getComment());
    assertEquals(hotel, feedback.getHotel());
  }
}
