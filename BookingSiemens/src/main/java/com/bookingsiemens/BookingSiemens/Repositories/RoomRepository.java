package com.bookingsiemens.BookingSiemens.Repositories;

import com.bookingsiemens.BookingSiemens.Commons.Room;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, UUID> {
}
