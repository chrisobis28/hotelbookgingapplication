package com.bookingsiemens.BookingSiemens.Commons;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Table(name = "FEEDBACKS")
public class Feedback {

    @Id
    @Column(name="feedback_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private UUID feedback_id;

    @ManyToOne
    @JoinColumn(name="hotel_id")
    @Getter
    @Setter
    @JsonIgnore
    private Hotel hotel;

    @Column(name="stars")
    @Getter
    @Setter
    private int stars;

    @Column(name="comment")
    @Getter
    @Setter
    private String comment;

    /**
     * Constructor for Feedback
     * @param hotel the Hotel receiving the Feedback
     * @param stars the stars assigned with the Feedback
     * @param comment the comment on the Feedback
     */
    public Feedback(Hotel hotel, int stars, String comment) {
        this.hotel = hotel;
        this.stars = stars;
        this.comment = comment;
    }
}
