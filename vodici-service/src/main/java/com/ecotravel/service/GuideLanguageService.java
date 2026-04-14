package com.ecotravel.service;

import com.ecotravel.dtos.GuideLanguageDTO;
import com.ecotravel.mapper.GuideMapper;
import com.ecotravel.model.GuideLanguage;
import com.ecotravel.model.GuideProfile;
import com.ecotravel.model.Language;
import com.ecotravel.repository.GuideLanguageRepository;
import com.ecotravel.repository.GuideProfileRepository;
import com.ecotravel.repository.LanguageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GuideLanguageService {

    private final GuideLanguageRepository guideLanguageRepository;
    private final GuideProfileRepository guideProfileRepository;
    private final LanguageRepository languageRepository;
    private final GuideMapper guideMapper;

    public List<GuideLanguageDTO> getAll() {
        List<GuideLanguage> entities = guideLanguageRepository.findAll();
        return guideMapper.toGuideLanguageDtoList(entities);
    }

    public List<GuideLanguageDTO> getLanguagesByGuide(Long guideId) {
        List<GuideLanguage> entities = guideLanguageRepository.findByGuideId(guideId);
        return guideMapper.toGuideLanguageDtoList(entities);
    }

    @Transactional
    public GuideLanguageDTO assignLanguage(Long guideId, Long languageId) {
        GuideProfile guide = guideProfileRepository.findById(guideId)
                .orElseThrow(() -> new RuntimeException("GuideProfile nije pronađen sa ID: " + guideId));
        Language language = languageRepository.findById(languageId)
                .orElseThrow(() -> new RuntimeException("Jezik nije pronađen sa ID: " + languageId));

        GuideLanguage guideLanguage = new GuideLanguage();
        guideLanguage.setGuide(guide);
        guideLanguage.setLanguage(language);

        GuideLanguage saved = guideLanguageRepository.save(guideLanguage);
        return guideMapper.toGuideLanguageDto(saved);
    }

    @Transactional
    public GuideLanguageDTO updateLanguageAssignment(Long id, Long guideId, Long languageId) {
        GuideLanguage existing = guideLanguageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Veza GuideLanguage ID " + id + " ne postoji"));

        GuideProfile newGuide = guideProfileRepository.findById(guideId)
                .orElseThrow(() -> new RuntimeException("GuideProfile nije pronađen sa ID: " + guideId));
        Language newLanguage = languageRepository.findById(languageId)
                .orElseThrow(() -> new RuntimeException("Language nije pronađen sa ID: " + languageId));

        existing.setGuide(newGuide);
        existing.setLanguage(newLanguage);

        GuideLanguage updated = guideLanguageRepository.save(existing);
        return guideMapper.toGuideLanguageDto(updated);
    }

    public void removeLanguage(Long id) {
        if (!guideLanguageRepository.existsById(id)) {
            throw new RuntimeException("Nemoguće obrisati: Veza ne postoji.");
        }
        guideLanguageRepository.deleteById(id);
    }
}