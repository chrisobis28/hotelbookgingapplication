package com.bookingsiemens.BookingSiemens.Services;

import com.bookingsiemens.BookingSiemens.Commons.Reservation;
import com.bookingsiemens.BookingSiemens.CustomExceptions.DuplicatedInstanceException;
import com.bookingsiemens.BookingSiemens.CustomExceptions.FieldNullException;
import com.bookingsiemens.BookingSiemens.CustomExceptions.InstanceNotFoundException;
import com.bookingsiemens.BookingSiemens.Repositories.ReservationRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    /**
     * Constructor for the Reservation Service
     * @param reservationRepository - the Reservation Repository
     */
    @Autowired
    public ReservationService (ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    /**
     * Inserts a Reservation into the database
     * @param reservation - the Reservation to be inserted
     * @return - the inserted Reservation
     */
    public Reservation createReservation (Reservation reservation) {
        nullFieldChecker(reservation);
        List<Reservation> l = reservationRepository.findAll()
            .stream().filter(x -> x.getRoom().equals(reservation.getRoom()) && !(x.getEnd_date().before(reservation.getStart_date())
                || reservation.getEnd_date().before(x.getStart_date())))
            .toList();
        if(l.size() > 0) {
            throw new DuplicatedInstanceException("Can't create such a reservation because the room is booked during that timeframe.");
        }
        return reservationRepository.save(reservation);
    }

    /**
     * Deletes a Reservation entry from the database
     * @param reservationID - the id of the Reservation to be deleted
     */
    public void deleteReservation (UUID reservationID) {
        Reservation reservation = checkReservationExistence(reservationID);
        reservationRepository.deleteById(reservation.getReservation_id());
    }

    /**
     * Retrieve the Reservation from the Repository
     * @param reservationID - the id of the Reservation
     * @return - the Reservation
     */
    public Reservation getReservation (UUID reservationID) {
        return checkReservationExistence(reservationID);
    }

    /**
     * Edits a Reservation from the Repository
     * @param reservation - the Reservation to be edited
     * @return - the Edited Reservation
     */
    public Reservation editReservation (Reservation reservation) {
        Reservation reservation1 = checkReservationExistence(reservation.getReservation_id());
        return reservationRepository.save(reservation1);
    }

    /**
     * Check whether a Reservation id is present in the database
     * @param reservation_id - the id to be checked
     * @return - the Reservation retrieved
     * @throws RuntimeException - the Reservation could not be found or id is null
     */
    public Reservation checkReservationExistence (UUID reservation_id) throws RuntimeException {
        if(reservation_id == null) {
            throw new FieldNullException("Null id not accepted.");
        }
        Optional<Reservation> o = reservationRepository.findById(reservation_id);
        if(o.isEmpty()) {
            throw new InstanceNotFoundException("No such room could be found.");
        }
        return o.get();
    }

    /**
     * Helper method to check the fields of a Reservation for nullability
     * @param reservation - the Reservation to verify
     * @throws RuntimeException - A field is null
     */
    public void nullFieldChecker (Reservation reservation) throws RuntimeException {
        if(reservation == null || reservation.getRoom() == null || reservation.getStart_date() == null || reservation.getEnd_date() == null) {
            throw new FieldNullException("Null fields are not valid.");
        }
    }
}
