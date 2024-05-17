package com.bookingsiemens.BookingSiemens.Services;

import com.bookingsiemens.BookingSiemens.Commons.Room;
import com.bookingsiemens.BookingSiemens.CustomExceptions.DuplicatedInstanceException;
import com.bookingsiemens.BookingSiemens.CustomExceptions.FieldNullException;
import com.bookingsiemens.BookingSiemens.CustomExceptions.InstanceNotFoundException;
import com.bookingsiemens.BookingSiemens.Repositories.RoomRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    /**
     * Constructor for Room Service
     * @param roomRepository the Room Repository
     */
    @Autowired
    public RoomService (RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    /**
     * Creates a Room in the database
     * @param room - the Room offered for creation
     * @return - the created Room
     * @throws RuntimeException - the Room already exists in the database or format is not correct
     */
    public Room createRoom (Room room) throws RuntimeException {
        nullFieldChecker(room);
        List<Room> o = roomRepository.findAll()
            .stream()
            .filter(x -> x.getHotel().equals(room.getHotel()) && x.getRoom_number() == room.getRoom_number())
            .toList();
        if(o.size() > 0) {
            throw new DuplicatedInstanceException("Already existing instance.");
        }
        return roomRepository.save(room);
    }

    /**
     * Deletes a Room from the database
     * @param roomID the id of the Room to be deleted
     */
    public void deleteRoom (UUID roomID) {
        Room room = checkRoomExistence(roomID);
        roomRepository.deleteById(room.getRoom_id());
    }

    /**
     * Helper method to check whether a Room exists
     * @param room_id - the id of the Room to be localized
     * @return - the Room desired, provided it exists
     * @throws RuntimeException - Room was not found or the id is null
     */
    public Room checkRoomExistence (UUID room_id) throws RuntimeException {
        if(room_id == null) {
            throw new FieldNullException("Null id not accepted.");
        }
        Optional<Room> o = roomRepository.findById(room_id);
        if(o.isEmpty()) {
            throw new InstanceNotFoundException("No such room could be found.");
        }
        return o.get();
    }

    /**
     * Helper method to check the fields of a Room for nullability
     * @param room - the Room to verify
     * @throws RuntimeException - A field is null
     */
    public void nullFieldChecker (Room room) throws RuntimeException {
        if(room == null || !(room.getType()>0) || !(room.getRoom_number()>0) || !(room.getPrice()>0)
            || room.getReservations() == null || room.getHotel() == null) {
            throw new FieldNullException("Null fields are not valid.");
        }
    }
}
