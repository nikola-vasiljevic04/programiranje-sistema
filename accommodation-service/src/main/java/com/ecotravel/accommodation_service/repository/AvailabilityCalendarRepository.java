package com.ecotravel.accommodation_service.repository;

import com.ecotravel.accommodation_service.model.AvailabilityCalendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AvailabilityCalendarRepository extends JpaRepository<AvailabilityCalendar, Long> {
    List<AvailabilityCalendar> findByUnitId(Long unitId);

    @Query("SELECT COUNT(a) > 0 FROM AvailabilityCalendar a " +
            "WHERE a.unit.id = :unitId " +
            "AND (:checkIn < a.endDate AND :checkOut > a.startDate)")
    boolean existsOverlap(
            @Param("unitId") Long unitId,
            @Param("checkIn") LocalDate checkIn,
            @Param("checkOut") LocalDate checkOut);
}
