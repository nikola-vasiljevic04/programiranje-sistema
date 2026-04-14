package com.ecotravel.service;

import com.ecotravel.dtos.GuideProfileRequestDTO;
import com.ecotravel.dtos.GuideProfileResponseDTO;
import com.ecotravel.mapper.GuideMapper;
import com.ecotravel.model.GuideLanguage;
import com.ecotravel.model.GuideProfile;
import com.ecotravel.model.Language;
import com.ecotravel.repository.GuideLanguageRepository;
import com.ecotravel.repository.GuideProfileRepository;
import com.ecotravel.repository.LanguageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GuideProfileService {

    private final GuideProfileRepository guideProfileRepository;
    private final LanguageRepository languageRepository;
    private final GuideLanguageRepository guideLanguageRepository;
    private final GuideMapper guideMapper;

    public List<GuideProfileResponseDTO> getAllProfiles() {
        return guideProfileRepository.findAll().stream()
                .map(guideMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public GuideProfileResponseDTO getProfileById(Long id) {
        GuideProfile profile = guideProfileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("GuideProfile nije pronađen sa ID: " + id));
        return guideMapper.toResponseDTO(profile);
    }

    @Transactional
    public GuideProfileResponseDTO createProfile(GuideProfileRequestDTO dto) {
        GuideProfile profile = guideMapper.toEntity(dto);

        GuideProfile saved = guideProfileRepository.save(profile);

        if (dto.getLanguageIds() != null && !dto.getLanguageIds().isEmpty()) {
            linkLanguages(saved, dto.getLanguageIds());
        }

        saved.setCompleteness(calculateScore(saved));
        return guideMapper.toResponseDTO(guideProfileRepository.save(saved));
    }

    @Transactional
    public GuideProfileResponseDTO updateProfile(Long id, GuideProfileRequestDTO dto) {
        GuideProfile existing = guideProfileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("GuideProfile nije pronađen"));

        existing.setFirstName(dto.getFirstName());
        existing.setLastName(dto.getLastName());
        existing.setBio(dto.getBio());
        existing.setContactEmail(dto.getContactEmail());

        if (dto.getLanguageIds() != null) {
            linkLanguages(existing, dto.getLanguageIds());
        }

        existing.setCompleteness(calculateScore(existing));

        log.info("Profil ID {} ažuriran. Novi completeness: {}", id, existing.getCompleteness());
        return guideMapper.toResponseDTO(guideProfileRepository.save(existing));
    }

    private void linkLanguages(GuideProfile guide, List<Long> languageIds) {
        guideLanguageRepository.deleteByGuide(guide);

        if (languageIds.isEmpty()) return;

        List<Language> languages = languageRepository.findAllById(languageIds);

        List<GuideLanguage> newVeze = languages.stream()
                .map(lang -> {
                    GuideLanguage gl = new GuideLanguage();
                    gl.setGuide(guide);
                    gl.setLanguage(lang);
                    return gl;
                })
                .collect(Collectors.toList());

        guideLanguageRepository.saveAll(newVeze);
        guide.setGuideLanguages(newVeze);
    }

    @Transactional
    public Double calculateCompleteness(Long id) {
        GuideProfile profile = guideProfileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profil nije pronađen"));

        Double score = calculateScore(profile);
        profile.setCompleteness(score);
        guideProfileRepository.save(profile);

        return score;
    }

    private Double calculateScore(GuideProfile profile) {
        double score = 0;
        double totalFields = 6;

        if (profile.getFirstName() != null && !profile.getFirstName().isBlank()) score++;
        if (profile.getLastName() != null && !profile.getLastName().isBlank()) score++;
        if (profile.getBio() != null && !profile.getBio().isBlank()) score++;
        if (profile.getContactEmail() != null && !profile.getContactEmail().isBlank()) score++;

        if (profile.getGuideLanguages() != null && !profile.getGuideLanguages().isEmpty()) score++;
        if (profile.getCertifications() != null && !profile.getCertifications().isEmpty()) score++;

        return (score / totalFields) * 100;
    }

    public void deleteProfile(Long id) {
        guideProfileRepository.deleteById(id);
    }
}