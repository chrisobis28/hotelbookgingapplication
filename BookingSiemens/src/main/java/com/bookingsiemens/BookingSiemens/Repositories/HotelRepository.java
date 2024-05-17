package com.bookingsiemens.BookingSiemens.Repositories;

import com.bookingsiemens.BookingSiemens.Commons.Hotel;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, UUID> {
}
