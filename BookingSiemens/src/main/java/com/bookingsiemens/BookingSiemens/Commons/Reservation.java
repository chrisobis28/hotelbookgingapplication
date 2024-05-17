package com.bookingsiemens.BookingSiemens.Commons;

import com.bookingsiemens.BookingSiemens.CustomExceptions.DateFormatIncorrectException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Table(name="RESERVATIONS")
public class Reservation {

    @Id
    @Column(name="reservation_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private UUID reservation_id;

    @ManyToOne
    @JoinColumn(name="room_id")
    @Getter
    @Setter
    @JsonIgnore
    private Room room;

    @Column(name="start_date")
    @Getter
    @Setter
    private Date start_date;

    @Column(name="end_date")
    @Getter
    @Setter
    private Date end_date;

    /**
     * Constructor for Reservation
     * @param room the Room being reserved
     * @param start_date the chosen start date
     * @param end_date the chosen end date
     */
    public Reservation(Room room, String start_date, String end_date) throws DateFormatIncorrectException {
        this.room = room;
        SimpleDateFormat parser = new SimpleDateFormat("dd/MM/yyyy");
        try {
            this.start_date = parser.parse(start_date);
            this.end_date = parser.parse(end_date);
        }
        catch(ParseException e) {
            throw new DateFormatIncorrectException("Incorrect Data Format.");
        }
    }
}
