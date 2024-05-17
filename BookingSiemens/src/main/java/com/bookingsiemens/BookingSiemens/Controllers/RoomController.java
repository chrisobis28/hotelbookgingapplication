package com.bookingsiemens.BookingSiemens.Controllers;

import com.bookingsiemens.BookingSiemens.Commons.Room;
import com.bookingsiemens.BookingSiemens.CustomExceptions.DuplicatedInstanceException;
import com.bookingsiemens.BookingSiemens.CustomExceptions.FieldNullException;
import com.bookingsiemens.BookingSiemens.CustomExceptions.InstanceNotFoundException;
import com.bookingsiemens.BookingSiemens.Routes;
import com.bookingsiemens.BookingSiemens.Services.RoomService;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Routes.ROOMS)
public class RoomController {

    private final RoomService roomService;

    /**
     * Constructor for the Room Controller
     * @param roomService the given Room Service
     */
    @Autowired
    public RoomController(RoomService roomService) {
      this.roomService = roomService;
    }

    /**
     * Creates a Room in the database
     * @param room - the Room to be created
     * @return - the Room that was created
     */
    @PostMapping("")
    public ResponseEntity<Room> createRoom (@RequestBody Room room) {
        try {
            return ResponseEntity.ok(roomService.createRoom(room));
        }
        catch(FieldNullException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        catch(DuplicatedInstanceException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    /**
     * Deletes a Room from the database
     * @param room_id - the UUID of the Room to be deleted
     * @return - status of the deletion
     */
    @DeleteMapping("/{roomId}")
    public ResponseEntity<String> deleteRoom (@PathVariable("roomId") UUID room_id) {
        try {
            roomService.deleteRoom(room_id);
            return ResponseEntity.status(HttpStatus.OK).body("Success.");
        }
        catch(FieldNullException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        catch(InstanceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
