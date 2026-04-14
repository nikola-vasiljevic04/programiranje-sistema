package com.ecotravel.controller;

import com.ecotravel.dtos.LanguageDTO;
import com.ecotravel.mapper.GuideMapper;
import com.ecotravel.model.Language;
import com.ecotravel.service.LanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/languages")
@RequiredArgsConstructor
public class LanguageController {

    private final LanguageService languageService;
    private final GuideMapper mapper;

    @GetMapping
    public List<LanguageDTO> getAll() {
        return languageService.getAllLanguages().stream()
                .map(mapper::toLanguageDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LanguageDTO> getById(@PathVariable Long id) {
        Language entity = languageService.getLanguageById(id);
        return ResponseEntity.ok(mapper.toLanguageDTO(entity));
    }

    @PostMapping
    public ResponseEntity<LanguageDTO> create(@RequestBody LanguageDTO dto) {
        var entity = languageService.createLanguage(mapper.toLanguageEntity(dto));
        return ResponseEntity.ok(mapper.toLanguageDTO(entity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LanguageDTO> update(@PathVariable Long id, @RequestBody LanguageDTO dto) {
        var entity = languageService.updateLanguage(id, mapper.toLanguageEntity(dto));
        return ResponseEntity.ok(mapper.toLanguageDTO(entity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        languageService.deleteLanguage(id);
        return ResponseEntity.noContent().build();
    }
}