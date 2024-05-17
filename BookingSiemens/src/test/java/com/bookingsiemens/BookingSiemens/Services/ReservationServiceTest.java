package com.bookingsiemens.BookingSiemens.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bookingsiemens.BookingSiemens.Commons.Reservation;
import com.bookingsiemens.BookingSiemens.Commons.Room;
import com.bookingsiemens.BookingSiemens.CustomExceptions.DuplicatedInstanceException;
import com.bookingsiemens.BookingSiemens.CustomExceptions.FieldNullException;
import com.bookingsiemens.BookingSiemens.CustomExceptions.InstanceNotFoundException;
import com.bookingsiemens.BookingSiemens.Repositories.ReservationRepository;
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
public class ReservationServiceTest {

  @Mock
  private ReservationRepository reservationRepository;
  private ReservationService reservationService;

  @BeforeEach
  void setUp() {
    reservationRepository = Mockito.mock(ReservationRepository.class);
    reservationService = new ReservationService(reservationRepository);
  }

  @Test
  void createReservationTestNull() {
    assertThrows(FieldNullException.class, () -> reservationService.createReservation(null));
  }

  @Test
  void createReservationTestAlreadyBooked() {
    Room room1 = new Room();
    Reservation r1 = new Reservation(room1, "12/12/2022", "16/12/2022");
    Reservation r2 = new Reservation(room1, "13/12/2022", "18/12/2022");
    when(reservationRepository.findAll()).thenReturn(List.of(r1));
    assertThrows(DuplicatedInstanceException.class, () -> reservationService.createReservation(r2));
  }

  @Test
  void createReservationTestSuccess() {
    Room room1 = new Room();
    Reservation r1 = new Reservation(room1, "12/12/2022", "13/12/2022");
    Reservation r2 = new Reservation(room1, "14/12/2022", "18/12/2022");
    when(reservationRepository.findAll()).thenReturn(List.of(r1));
    when(reservationRepository.save(r2)).thenReturn(r2);
    Reservation r3 = reservationService.createReservation(r2);
    assertEquals(r2, r3);
  }

  @Test
  void deleteReservationTestNullId() {
    assertThrows(FieldNullException.class, () -> reservationService.deleteReservation(null));
  }

  @Test
  void deleteReservationTestInstanceNotFound() {
    UUID id = UUID.randomUUID();
    when(reservationRepository.findById(id)).thenReturn(Optional.empty());
    assertThrows(InstanceNotFoundException.class, () -> reservationService.deleteReservation(id));
  }

  @Test
  void deleteReservationTestSuccess() {
    Reservation reservation = new Reservation(new Room(), "12/12/2022", "14/12/2022");
    reservation.setReservation_id(UUID.randomUUID());
    when(reservationRepository.findById(reservation.getReservation_id())).thenReturn(Optional.of(reservation));
    reservationService.deleteReservation(reservation.getReservation_id());
    verify(reservationRepository).deleteById(reservation.getReservation_id());
  }

  @Test
  void getReservationTestNull() {
    assertThrows(FieldNullException.class, () -> reservationService.getReservation(null));
  }

  @Test
  void getReservationTestNotFound() {
    UUID id = UUID.randomUUID();
    when(reservationRepository.findById(id)).thenReturn(Optional.empty());
    assertThrows(InstanceNotFoundException.class, () -> reservationService.getReservation(id));
  }

  @Test
  void getReservationTestSuccess() {
    Reservation reservation = new Reservation(new Room(), "12/12/2022", "14/12/2022");
    reservation.setReservation_id(UUID.randomUUID());
    when(reservationRepository.findById(reservation.getReservation_id())).thenReturn(Optional.of(reservation));
    assertEquals(reservation, reservationService.getReservation(reservation.getReservation_id()));
  }

  @Test
  void editReservationTestNull() {
    Reservation r = new Reservation(null, "14/05/2015", "15/05/2015");
    assertThrows(FieldNullException.class, () -> reservationService.editReservation(r));
  }

  @Test
  void editReservationTestNotFound() {
    Reservation r = new Reservation(new Room(), "14/05/2015", "15/05/2015");
    r.setReservation_id(UUID.randomUUID());
    when(reservationRepository.findById(r.getReservation_id())).thenReturn(Optional.empty());
    assertThrows(InstanceNotFoundException.class, () -> reservationService.editReservation(r));
  }

  @Test
  void editReservationTestSuccess() {
    Reservation r = new Reservation(new Room(), "14/05/2015", "15/05/2015");
    r.setReservation_id(UUID.randomUUID());
    when(reservationRepository.findById(r.getReservation_id())).thenReturn(Optional.of(r));
    when(reservationRepository.save(r)).thenReturn(r);
    assertEquals(r, reservationService.editReservation(r));
  }
}
