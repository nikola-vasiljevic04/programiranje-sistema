package com.ecotravel.repository;

import com.ecotravel.model.AdventureCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdventureCategoryRepository extends JpaRepository<AdventureCategory, Long> {
}