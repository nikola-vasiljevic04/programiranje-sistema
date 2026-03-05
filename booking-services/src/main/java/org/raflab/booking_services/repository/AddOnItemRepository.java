package org.raflab.booking_services.repository;

import org.raflab.booking_services.model.AddOnItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AddOnItemRepository extends JpaRepository<AddOnItem, Long> {
    @Query("select i from AddOnItem i where i.booking.id = :bookingId")
    List<AddOnItem> findByBookingId(Long bookingId);
}
