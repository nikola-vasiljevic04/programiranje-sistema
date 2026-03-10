package com.ecotravel.accommodation_service.repository;

import com.ecotravel.accommodation_service.model.PriceTier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceTierRepository extends JpaRepository<PriceTier, Long> {
}
