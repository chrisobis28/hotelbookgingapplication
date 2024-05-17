package com.bookingsiemens.BookingSiemens.Commons;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@NoArgsConstructor
@Table(name = "ROOMS")
public class Room {

    @Id
    @Column(name="room_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private UUID room_id;

    @ManyToOne
    @JoinColumn(name="hotel_id")
    @Getter
    @Setter
    @JsonIgnore
    private Hotel hotel;

    @Column(name="room_number")
    @Getter
    @Setter
    private int room_number;

    @Column(name="type")
    @Getter
    @Setter
    private int type;

    @Column(name="price")
    @Getter
    @Setter
    private int price;

    @Getter
    @Setter
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action= OnDeleteAction.CASCADE)
    @JoinColumn(name="room_id")
    private List<Reservation> reservations;

    /**
     * Constructor for the Room
     * @param hotel the Hotel of the Room
     * @param room_number the number of the Room
     * @param type the type of the Room
     * @param price the price of the Room
     */
    public Room(Hotel hotel, int room_number, int type, int price) {
        this.hotel = hotel;
        this.room_number = room_number;
        this.type = type;
        this.price = price;
        reservations = new ArrayList<>();
    }

    /**
     * Constructor for the Room when the Room already has some Reservations
     * @param hotel the Hotel of the Room
     * @param room_number the number of the Room
     * @param type the type of the Room
     * @param price the price of the Room
     */

    public Room(Hotel hotel, int room_number, int type, int price, List<Reservation> reservations) {
        this.hotel = hotel;
        this.room_number = room_number;
        this.type = type;
        this.price = price;
        this.reservations = reservations;
    }
}
