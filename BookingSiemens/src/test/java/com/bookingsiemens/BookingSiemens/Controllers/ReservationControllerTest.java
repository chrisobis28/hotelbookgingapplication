package com.bookingsiemens.BookingSiemens.Controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.bookingsiemens.BookingSiemens.Commons.Reservation;
import com.bookingsiemens.BookingSiemens.CustomExceptions.DuplicatedInstanceException;
import com.bookingsiemens.BookingSiemens.CustomExceptions.FieldNullException;
import com.bookingsiemens.BookingSiemens.CustomExceptions.InstanceNotFoundException;
import com.bookingsiemens.BookingSiemens.Services.ReservationService;
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
public class ReservationControllerTest {

  @Mock
  private ReservationService reservationService;

  private ReservationController reservationController;

  @BeforeEach
  void setUp() {
    reservationService = Mockito.mock(ReservationService.class);
    reservationController = new ReservationController(reservationService);
  }

  @Test
  void createReservationTestNullField() {
    when(reservationService.createReservation(any())).thenThrow(FieldNullException.class);
    ResponseEntity<Reservation> r = reservationController.addReservation(new Reservation());
    assertEquals(HttpStatus.BAD_REQUEST, r.getStatusCode());
    assertNull(r.getBody());
  }

  @Test
  void createReservationTestInterfered() {
    when(reservationService.createReservation(any())).thenThrow(DuplicatedInstanceException.class);
    ResponseEntity<Reservation> r = reservationController.addReservation(new Reservation());
    assertEquals(HttpStatus.FORBIDDEN, r.getStatusCode());
    assertNull(r.getBody());
  }

  @Test
  void createReservationTestSuccess() {
    Reservation reservation = new Reservation();
    when(reservationService.createReservation(any())).thenReturn(reservation);
    ResponseEntity<Reservation> r = reservationController.addReservation(new Reservation());
    assertEquals(HttpStatus.OK, r.getStatusCode());
    assertEquals(reservation, r.getBody());
  }

  @Test
  void deleteReservationTestNullField() {
    doThrow(new FieldNullException("Null id.")).when(reservationService).deleteReservation(any());
    ResponseEntity<String> r = reservationController.deleteReservation(UUID.randomUUID());
    assertEquals(HttpStatus.BAD_REQUEST, r.getStatusCode());
    assertEquals("Null id.", r.getBody());
  }

  @Test
  void deleteReservationTestNotFound() {
    doThrow(new InstanceNotFoundException("Reservation not found.")).when(reservationService).deleteReservation(any());
    ResponseEntity<String> r = reservationController.deleteReservation(UUID.randomUUID());
    assertEquals(HttpStatus.NOT_FOUND, r.getStatusCode());
    assertEquals("Reservation not found.", r.getBody());
  }

  @Test
  void deleteReservationTestSuccess() {
    doNothing().when(reservationService).deleteReservation(any());
    ResponseEntity<String> r = reservationController.deleteReservation(UUID.randomUUID());
    assertEquals(HttpStatus.OK, r.getStatusCode());
    assertEquals("Success.", r.getBody());
  }

  @Test
  void getReservationTestNullField() {
    when(reservationService.getReservation(any())).thenThrow(FieldNullException.class);
    ResponseEntity<Reservation> r = reservationController.getReservation(UUID.randomUUID());
    assertEquals(HttpStatus.BAD_REQUEST, r.getStatusCode());
    assertNull(r.getBody());
  }

  @Test
  void getReservationTestNotFound() {
    when(reservationService.getReservation(any())).thenThrow(InstanceNotFoundException.class);
    ResponseEntity<Reservation> r = reservationController.getReservation(UUID.randomUUID());
    assertEquals(HttpStatus.NOT_FOUND, r.getStatusCode());
    assertNull(r.getBody());
  }

  @Test
  void getReservationTestSuccess() {
    UUID id = UUID.randomUUID();
    Reservation r = new Reservation();
    r.setReservation_id(id);
    when(reservationService.getReservation(id)).thenReturn(r);
    ResponseEntity<Reservation> re = reservationController.getReservation(id);
    assertEquals(HttpStatus.OK, re.getStatusCode());
    assertEquals(r, re.getBody());
  }

  @Test
  void editReservationTestNullField() {
    when(reservationService.editReservation(any())).thenThrow(FieldNullException.class);
    ResponseEntity<Reservation> r = reservationController.editReservation(new Reservation());
    assertEquals(HttpStatus.BAD_REQUEST, r.getStatusCode());
    assertNull(r.getBody());
  }

  @Test
  void editReservationTestNotFound() {
    when(reservationService.editReservation(any())).thenThrow(InstanceNotFoundException.class);
    ResponseEntity<Reservation> r = reservationController.editReservation(new Reservation());
    assertEquals(HttpStatus.NOT_FOUND, r.getStatusCode());
    assertNull(r.getBody());
  }

  @Test
  void editReservationTestSuccess() {
    Reservation reservation = new Reservation();
    when(reservationService.editReservation(any())).thenReturn(reservation);
    ResponseEntity<Reservation> r = reservationController.editReservation(new Reservation());
    assertEquals(HttpStatus.OK, r.getStatusCode());
    assertEquals(reservation, r.getBody());
  }
}
