package com.ecotravel.service;

import com.ecotravel.dtos.AdventureCategoryDTO;
import com.ecotravel.mapper.AdventureMapper;
import com.ecotravel.model.AdventureCategory;
import com.ecotravel.repository.AdventureCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdventureCategoryService {

    private final AdventureCategoryRepository categoryRepository;
    private final AdventureMapper adventureMapper;

    // 1. CREATE
    public AdventureCategoryDTO createCategory(AdventureCategoryDTO dto) {
        AdventureCategory category = adventureMapper.toEntity(dto);
        return adventureMapper.toDTO(categoryRepository.save(category));
    }

    // 2. GET ALL
    public List<AdventureCategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(adventureMapper::toDTO)
                .collect(Collectors.toList());
    }

    // 3. GET BY ID
    public AdventureCategoryDTO getCategoryById(Long id) {
        AdventureCategory category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kategorija sa ID " + id + " nije pronađena"));
        return adventureMapper.toDTO(category);
    }

    // 4. UPDATE
    public AdventureCategoryDTO updateCategory(Long id, AdventureCategoryDTO dto) {
        // Prvo proveri da li kategorija uopšte postoji u bazi
        AdventureCategory existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Neuspešna izmena: Kategorija nije pronađena"));

        // Ažuriraj polja
        existingCategory.setName(dto.getName());
        existingCategory.setDescription(dto.getDescription());

        // Sačuvaj izmene
        AdventureCategory savedCategory = categoryRepository.save(existingCategory);
        return adventureMapper.toDTO(savedCategory);
    }

    // 5. DELETE
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Neuspešno brisanje: Kategorija ne postoji");
        }

        try {
            categoryRepository.deleteById(id);
        } catch (Exception e) {
            // Ovo se dešava ako pokušaš da obrišeš kategoriju koja ima vezane avanture (Foreign Key Constraint)
            throw new RuntimeException("Nije moguće obrisati kategoriju jer postoje avanture vezane za nju.");
        }
    }
}