package com.ecotravel.accommodation_service.repository;

import com.ecotravel.accommodation_service.model.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {
}
