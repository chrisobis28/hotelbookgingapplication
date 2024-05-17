package com.bookingsiemens.BookingSiemens.Repositories;

import com.bookingsiemens.BookingSiemens.Commons.Feedback;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, UUID> {
}
