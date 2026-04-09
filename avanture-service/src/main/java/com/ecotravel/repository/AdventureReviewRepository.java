package com.ecotravel.repository;

import com.ecotravel.model.AdventureReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdventureReviewRepository extends JpaRepository<AdventureReview, Long> {
    List<AdventureReview> findByAdventureId(Long adventureId);
}