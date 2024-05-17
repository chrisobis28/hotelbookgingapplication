package com.bookingsiemens.BookingSiemens.Commons;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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

@NoArgsConstructor
@Entity
@Table(name = "HOTELS")
public class Hotel {
    @Id
    @Column(name="hotel_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private UUID hotel_id;

    @Column(name="hotel_name")
    @Getter
    @Setter
    private String hotel_name;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "latitude", column = @Column(name = "latitude")),
        @AttributeOverride(name = "longitude", column = @Column(name = "longitude"))
    })
    @Getter
    @Setter
    private Location hotel_location;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action= OnDeleteAction.CASCADE)
    @JoinColumn(name="hotel_id")
    @Getter
    @Setter
    private List<Room> rooms;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action= OnDeleteAction.CASCADE)
    @JoinColumn(name="hotel_id")
    @Getter
    @Setter
    private List<Feedback> feedbacks;

    /**
     * Constructor for the Hotel when list of Rooms is unknown
     * @param hotel_name the Hotel's Name
     * @param hotel_location the Hotel's Location
     */
    public Hotel(String hotel_name, Location hotel_location) {
        this.hotel_name = hotel_name;
        this.hotel_location = hotel_location;
        this.rooms = new ArrayList<>();
        this.feedbacks = new ArrayList<>();
    }

    /**
     * Constructor for the Hotel when list of Rooms is known
     * @param hotel_name the Hotel's Name
     * @param hotel_location the Hotel's Location
     * @param rooms the Hotel's Rooms list
     */
    public Hotel(String hotel_name, Location hotel_location, List<Room> rooms) {
        this.hotel_name = hotel_name;
        this.hotel_location = hotel_location;
        this.rooms = rooms;
        this.feedbacks = new ArrayList<>();
    }

    /**
     * Constructor for the Hotel when list of Rooms is known
     * @param hotel_name the Hotel's Name
     * @param hotel_location the Hotel's Location
     * @param rooms the Hotel's Rooms list
     * @param feedbacks the Hotel's feedbacks
     */
    public Hotel(String hotel_name, Location hotel_location, List<Room> rooms, List<Feedback> feedbacks) {
        this.hotel_name = hotel_name;
        this.hotel_location = hotel_location;
        this.rooms = rooms;
        this.feedbacks = feedbacks;
    }
}