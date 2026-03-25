package org.raflab.booking_services.repository;

import org.raflab.booking_services.model.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GuestRepository extends JpaRepository<Guest, Long> {

    @Query("select count(email) > 0 from Guest where email = :email")
    boolean existsByEmail(String email);
}
