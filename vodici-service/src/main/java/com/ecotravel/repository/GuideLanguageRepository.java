package com.ecotravel.repository;

import com.ecotravel.model.GuideLanguage;
import com.ecotravel.model.GuideProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GuideLanguageRepository extends JpaRepository<GuideLanguage, Long> {
    List<GuideLanguage> findByGuideId(Long guideId);
    void deleteByGuide(GuideProfile guide);
    }