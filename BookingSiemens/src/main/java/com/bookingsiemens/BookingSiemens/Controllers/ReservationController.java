package com.bookingsiemens.BookingSiemens.Controllers;

import com.bookingsiemens.BookingSiemens.Commons.Reservation;
import com.bookingsiemens.BookingSiemens.CustomExceptions.DuplicatedInstanceException;
import com.bookingsiemens.BookingSiemens.CustomExceptions.FieldNullException;
import com.bookingsiemens.BookingSiemens.CustomExceptions.InstanceNotFoundException;
import com.bookingsiemens.BookingSiemens.Routes;
import com.bookingsiemens.BookingSiemens.Services.ReservationService;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Routes.RESERVATIONS)
public class ReservationController {

    private final ReservationService reservationService;

    /**
     * Constructor for the Reservation
     * @param reservationService - the Reservation Service
     */
    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    /**
     * Adds a Reservation to the database
     * @param reservation - the Reservation to be added
     * @return - the Reservation that was just added
     */
    @PostMapping("")
    public ResponseEntity<Reservation> addReservation(@RequestBody Reservation reservation) {
        try {
            return ResponseEntity.ok(reservationService.createReservation(reservation));
        }
        catch(FieldNullException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        catch(DuplicatedInstanceException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    /**
     * Deletes a Reservation from the database
     * @param reservation_id - the id of the Reservation to be deleted
     * @return - the status of the deletion
     */
    @DeleteMapping("/{reservationId}")
    public ResponseEntity<String> deleteReservation(@PathVariable("reservationId") UUID reservation_id) {
        try {
            reservationService.deleteReservation(reservation_id);
            return ResponseEntity.status(HttpStatus.OK).body("Success.");
        }
        catch(FieldNullException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        catch(InstanceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Gets a Reservation from the database
     * @param reservation_id - the id of the Reservation to be retrieved
     * @return - the Reservation
     */
    @GetMapping("/{reservationId}")
    public ResponseEntity<Reservation> getReservation(@PathVariable("reservationId") UUID reservation_id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(reservationService.getReservation(reservation_id));
        }
        catch(FieldNullException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        catch(InstanceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Edits a Reservation in the database
     * @param reservation - the Reservation for replacement
     * @return - the Reservation
     */
    @PutMapping("")
    public ResponseEntity<Reservation> editReservation(@RequestBody Reservation reservation) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(reservationService.editReservation(reservation));
        }
        catch(FieldNullException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        catch(InstanceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
