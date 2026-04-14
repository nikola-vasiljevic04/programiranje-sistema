package com.ecotravel.repository;

import com.ecotravel.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {
    java.util.Optional<Language> findByName(String name);
}