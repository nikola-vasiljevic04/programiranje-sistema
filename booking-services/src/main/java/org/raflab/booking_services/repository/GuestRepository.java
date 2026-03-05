package org.raflab.booking_services.repository;

import org.raflab.booking_services.model.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest, Long> {
}
