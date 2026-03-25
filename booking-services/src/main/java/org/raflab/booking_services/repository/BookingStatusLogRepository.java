package org.raflab.booking_services.repository;

import org.raflab.booking_services.model.BookingStatusLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingStatusLogRepository extends JpaRepository<BookingStatusLog, Long> {
    @Query("select l from BookingStatusLog l where l.booking.id = :bookingId order by l.changedAt desc")
    List<BookingStatusLog> findByBookingIdOrderByChangedAtDesc(Long bookingId);
}
