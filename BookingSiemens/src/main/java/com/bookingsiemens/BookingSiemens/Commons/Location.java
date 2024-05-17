package com.bookingsiemens.BookingSiemens.Commons;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@NoArgsConstructor
public class Location {

    @Getter
    @Setter
    private double latitude;
    @Getter
    @Setter
    private double longitude;

    /**
     * Constructor for the Location
     * @param latitude the latitude of the location
     * @param longitude the longitude of the location
     */
    public Location(float latitude, float longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
