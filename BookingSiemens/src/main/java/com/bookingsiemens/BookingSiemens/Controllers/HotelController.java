package com.bookingsiemens.BookingSiemens.Controllers;

import com.bookingsiemens.BookingSiemens.Commons.Hotel;
import com.bookingsiemens.BookingSiemens.Commons.Location;
import com.bookingsiemens.BookingSiemens.Commons.Room;
import com.bookingsiemens.BookingSiemens.CustomExceptions.FieldNullException;
import com.bookingsiemens.BookingSiemens.CustomExceptions.InstanceNotFoundException;
import com.bookingsiemens.BookingSiemens.Routes;
import com.bookingsiemens.BookingSiemens.Services.GeolocationService;
import com.bookingsiemens.BookingSiemens.Services.HotelService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Routes.HOTELS)
public class HotelController {

    private final HotelService hotelService;

    private final GeolocationService geolocationService;

    /**
     * Constructor for the Hotel Controller
     * @param hotelService - the Hotel Service
     * @param geolocationService - the Geolocation Service for user localization
     */
    @Autowired
    public HotelController(HotelService hotelService, GeolocationService geolocationService) {
        this.hotelService = hotelService;
        this.geolocationService = geolocationService;
    }

    /**
     * Adds Hotel entries into the database
     * @param hotelList - the list of Hotels to be added
     * @return - the list of Hotels that were added
     */
    @PostMapping("")
    public ResponseEntity<List<Hotel>> addHotels(@RequestBody List<Hotel> hotelList) {
        try {
            return ResponseEntity.ok(hotelService.addHotels(hotelList));
        }
        catch(FieldNullException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Deletes a Hotel entry from the database
     * @param hotel_id - the id of the Hotel to be deleted
     * @return - the deletion status
     */
    @DeleteMapping("/{hotelId}")
    public ResponseEntity<String> deleteHotel(@PathVariable("hotelId") UUID hotel_id) {
        try {
            hotelService.deleteHotel(hotel_id);
            return ResponseEntity.status(HttpStatus.OK).body("Success");
        }
        catch(FieldNullException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        catch(InstanceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Gets the Rooms of a Hotel
     * @param hotel_id - the id of the Hotel whose Rooms to retrieve
     * @return - the list of Rooms retrieved
     */
    @GetMapping("/{hotelId}")
    public ResponseEntity<List<Room>> getHotelRooms(@PathVariable("hotelId") UUID hotel_id) {
        try {
            return ResponseEntity.ok(hotelService.getHotelRooms(hotel_id));
        }
        catch(FieldNullException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        catch(InstanceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/all/{hotelId}")
    public ResponseEntity<Hotel> getHotel(@PathVariable("hotelId") UUID hotel_id) {
        try {
            return ResponseEntity.ok(hotelService.getHotel(hotel_id));
        }
        catch(FieldNullException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        catch(InstanceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Given a radius, localizing the user, retrieves the Hotels within the range
     * @param request - the User Request's information
     * @param radius - the radius of the searching area
     * @return - the list of Hotels found
     */
    @GetMapping("/getHotelsInRadius/{radius}")
    public ResponseEntity<List<Hotel>> getHotelsNearby(HttpServletRequest request, @PathVariable("radius") float radius) {
        String clientIp = request.getHeader("X-FORWARDED-FOR");
        if (clientIp == null || "".equals(clientIp)) {
            clientIp = request.getRemoteAddr();
        }

        Location userLocation = geolocationService.getGeolocation(clientIp);
        return ResponseEntity.ok(hotelService.getAllHotelsNearby(userLocation.getLatitude(), userLocation.getLongitude(), radius));
    }
}
