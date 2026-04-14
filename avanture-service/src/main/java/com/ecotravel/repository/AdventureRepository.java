package com.ecotravel.repository;

import com.ecotravel.model.Adventure;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdventureRepository extends JpaRepository<Adventure, Long> {
    @Query("SELECT a FROM Adventure a JOIN a.category c WHERE c.name = :categoryName")
    List<Adventure> findByWeightAndCategory(@Param("categoryName") String categoryName);

    @Query("SELECT a FROM Adventure a WHERE a.basePrice < (SELECT AVG(a2.basePrice) FROM Adventure a2)")
    List<Adventure> findAdventuresCheaperThanAverage();
}