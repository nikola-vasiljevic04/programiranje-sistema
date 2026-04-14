package com.ecotravel.repository;

import com.ecotravel.model.GuideProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuideProfileRepository extends JpaRepository<GuideProfile, Long> {

}