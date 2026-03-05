package org.raflab.booking_services.repository;

import org.raflab.booking_services.model.BookingStatusLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingStatusLogRepository extends JpaRepository<BookingStatusLog, Long> {
}
