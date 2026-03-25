package org.raflab.booking_services.repository;

import org.raflab.booking_services.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
