package com.ecotravel.accommodation_service.repository;

import com.ecotravel.accommodation_service.model.Facility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, Long> {
}
