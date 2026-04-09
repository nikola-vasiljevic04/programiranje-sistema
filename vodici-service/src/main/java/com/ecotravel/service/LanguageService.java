package com.ecotravel.service;

import com.ecotravel.model.Language;
import com.ecotravel.repository.LanguageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LanguageService {

    private final LanguageRepository languageRepository;

    public List<Language> getAllLanguages() {
        return languageRepository.findAll();
    }

    public Language getLanguageById(Long id) {
        return languageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jezik nije pronađen"));
    }

    public Language createLanguage(Language language) {
        language.setId(null); // Osigurava da se uvek radi INSERT, a ne UPDATE
        return languageRepository.save(language);
    }

    public Language updateLanguage(Long id, Language details) {
        Language l = getLanguageById(id);
        l.setName(details.getName());
        return languageRepository.save(l);
    }

    public void deleteLanguage(Long id) {
        languageRepository.deleteById(id);
    }
}