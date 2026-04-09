package com.ecotravel.service;

import com.ecotravel.dtos.AdventureRequestDTO;
import com.ecotravel.dtos.AdventureResponseDTO;
import com.ecotravel.mapper.AdventureMapper;
import com.ecotravel.model.Adventure;
import com.ecotravel.model.AdventureCategory;
import com.ecotravel.repository.AdventureCategoryRepository;
import com.ecotravel.repository.AdventureRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdventureService {

    private final AdventureRepository adventureRepository;
    private final AdventureCategoryRepository categoryRepository;
    private final AdventureMapper adventureMapper;

    /**
     * Vraća sirovi entitet iz baze.
     * Neophodno za Facade sloj kako bi dobio guideId bez dodatnog mapiranja.
     */
    public Adventure getRawById(Long id) {
        return adventureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Avantura sa ID " + id + " nije pronađena"));
    }

    public AdventureResponseDTO getById(Long id) {
        Adventure adventure = getRawById(id);
        log.info("Dohvaćena avantura iz baze: {}", adventure);
        return adventureMapper.toResponseDTO(adventure);
    }

    public List<Adventure> getAllRaw() {
        return adventureRepository.findAll();
    }

    @Transactional
    public AdventureResponseDTO createAdventure(AdventureRequestDTO dto) {
        AdventureCategory category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Kategorija nije pronađena"));

        Adventure adventure = adventureMapper.toEntity(dto);
        adventure.setCategory(category);
        // Eksplicitno setujemo guideId jer dolazi iz eksternog sistema (8082)
        adventure.setGuideId(dto.getGuideId());

        Adventure saved = adventureRepository.save(adventure);
        return adventureMapper.toResponseDTO(saved);
    }

    @Transactional
    public AdventureResponseDTO updateAdventure(Long id, AdventureRequestDTO dto) {
        Adventure existing = getRawById(id);

        AdventureCategory category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Kategorija nije pronađena"));

        existing.setTitle(dto.getTitle());
        existing.setDescription(dto.getDescription());
        existing.setDifficultyLevel(dto.getDifficultyLevel());
        existing.setBasePrice(dto.getBasePrice());
        existing.setCategory(category);
        existing.setGuideId(dto.getGuideId());

        return adventureMapper.toResponseDTO(adventureRepository.save(existing));
    }

    public void deleteAdventure(Long id) {
        if (!adventureRepository.existsById(id)) {
            throw new RuntimeException("Neuspešno brisanje: Avantura ne postoji.");
        }
        adventureRepository.deleteById(id);
    }

    // --- Metode za složene upite koje vraćaju entitete (za Facade obradu) ---

    public List<Adventure> getBudgetAdventuresRaw() {
        return adventureRepository.findAdventuresCheaperThanAverage();
    }

    public List<Adventure> getByCategoryRaw(String categoryName) {
        // Napomena: Proveri da li se metoda u AdventureRepository zove baš findByWeightAndCategory
        return adventureRepository.findByWeightAndCategory(categoryName);
    }
}