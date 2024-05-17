package com.bookingsiemens.BookingSiemens.Services;

import com.bookingsiemens.BookingSiemens.Commons.Hotel;
import com.bookingsiemens.BookingSiemens.Commons.Room;
import com.bookingsiemens.BookingSiemens.CustomExceptions.FieldNullException;
import com.bookingsiemens.BookingSiemens.CustomExceptions.InstanceNotFoundException;
import com.bookingsiemens.BookingSiemens.Repositories.HotelRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;

    /**
     * Constructor for the Hotel Service
     * @param hotelRepository - the Repository of Hotels
     */
    @Autowired
    public HotelService (HotelRepository hotelRepository) {
      this.hotelRepository = hotelRepository;
    }

    /**
     * Adding hotels to the database implementation
     * @param hotelList - list of Hotels to be added
     * @return - the list of Hotels added
     */
    public List<Hotel> addHotels  (List<Hotel> hotelList) {
        for(Hotel hotel : hotelList) {
            nullFieldChecker(hotel);
        }
        return hotelRepository.saveAll(hotelList);
    }

    /**
     * Retrieve the Rooms of a desired Hotel
     * @param hotelID - the id of the Hotel
     * @return - the list of Rooms
     */
    public List<Room> getHotelRooms (UUID hotelID) {
        Hotel hotel = checkHotelExistence(hotelID);
        return hotel.getRooms();
    }

    /**
     * Deletes a Hotel from the database
     * @param hotelID - the id of the Hotel to be deleted
     */
    public void deleteHotel (UUID hotelID) {
        Hotel hotel = checkHotelExistence(hotelID);
        hotelRepository.deleteById(hotel.getHotel_id());
    }

    /**
     * Retrieve Hotels within a range of the user
     * @param latitude - latitude of the user
     * @param longitude - longitude of the user
     * @param radius - the radius where to search for hotels
     * @return - the list of Hotels retrieved
     */
    public List<Hotel> getAllHotelsNearby (double latitude, double longitude, double radius) {
        List<Hotel> allHotels = hotelRepository.findAll();

        return allHotels.stream()
            .filter(hotel -> calculateDistance(
                latitude, longitude, hotel.getHotel_location().getLatitude(), hotel.getHotel_location().getLongitude()) <= radius)
            .toList();
    }

    public Hotel getHotel(UUID hotel_id) {
        return hotelRepository.findById(hotel_id).get();
    }

    /**
     * Calculates the distance between two points on the map
     * @param lat1 - latitude of the first point
     * @param lon1 - longitude of the first point
     * @param lat2 - latitude of the second point
     * @param lon2 - longitude of the second point
     * @return
     */
    public static double calculateDistance (double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
            Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return 6371 * c; // EARTH_RADIUS
    }

    /**
     * Checks whether a Hotel exists in the database
     * @param hotelID - the id of the Hotel for lookup
     * @return - the Hotel provided it exists
     * @throws RuntimeException - the id is not valid or the Hotel was not found
     */
    public Hotel checkHotelExistence (UUID hotelID) throws RuntimeException {
        if(hotelID == null) {
            throw new FieldNullException("Null id not accepted.");
        }
        Optional<Hotel> o = hotelRepository.findById(hotelID);
        if(o.isEmpty()) {
            throw new InstanceNotFoundException("No such room could be found.");
        }
        return o.get();
    }

    /**
     * Checks for the Hotel to not contain null fields
     * @param hotel - the Hotel for checking
     * @throws RuntimeException - the Hotel instance contains nulls
     */
    public void nullFieldChecker (Hotel hotel) throws RuntimeException {
        if(hotel.getHotel_name() == null || hotel.getHotel_location() == null) {
            throw new FieldNullException("Null fields are not valid.");
        }
    }
}
