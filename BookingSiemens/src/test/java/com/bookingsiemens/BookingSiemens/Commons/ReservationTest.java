package com.bookingsiemens.BookingSiemens.Commons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.bookingsiemens.BookingSiemens.CustomExceptions.DateFormatIncorrectException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.zip.DataFormatException;
import org.junit.jupiter.api.Test;

public class ReservationTest {

  @Test
  void testReservationConstructor() throws DataFormatException {
    String start_date = "15/03/2021";
    String end_date = "18/03/2021";
    Room room = new Room();
    Reservation reservation = new Reservation(room, start_date, end_date);
    assertEquals(room, reservation.getRoom());
    SimpleDateFormat parser = new SimpleDateFormat("dd/MM/yyyy");
    try {
      assertEquals(parser.parse(start_date), reservation.getStart_date());
      assertEquals(parser.parse(end_date), reservation.getEnd_date());
    }
    catch(ParseException e){
      throw new DataFormatException("Data format incorrect.");
    }
  }

  @Test
  void testReservationConstructorException() {
    String start_date = "152/2021";
    String end_date = "18/03/2021";
    Room room = new Room();
    assertThrows(
        DateFormatIncorrectException.class, () -> new Reservation(room, start_date, end_date));
  }
}
