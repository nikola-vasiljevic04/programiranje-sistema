package com.ecotravel.accommodation_service.repository;

import com.ecotravel.accommodation_service.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}