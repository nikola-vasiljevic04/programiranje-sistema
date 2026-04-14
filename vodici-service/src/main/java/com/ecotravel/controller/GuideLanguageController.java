package com.ecotravel.controller;

import com.ecotravel.dtos.GuideLanguageDTO;
import com.ecotravel.mapper.GuideMapper;
import com.ecotravel.service.GuideLanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/guide-languages")
@RequiredArgsConstructor
public class GuideLanguageController {

    private final GuideLanguageService guideLanguageService;
    private final GuideMapper guideMapper;

    @GetMapping
    public List<GuideLanguageDTO> getAll() {
        return guideLanguageService.getAll();
    }

    @GetMapping("/guide/{guideId}")
    public List<GuideLanguageDTO> getByGuide(@PathVariable Long guideId) {
        return guideLanguageService.getLanguagesByGuide(guideId);
    }

    @PostMapping("/assign")
    public ResponseEntity<GuideLanguageDTO> assign(@RequestParam Long guideId, @RequestParam Long languageId) {
        GuideLanguageDTO saved = guideLanguageService.assignLanguage(guideId, languageId);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GuideLanguageDTO> update(
            @PathVariable Long id,
            @RequestParam Long guideId,
            @RequestParam Long languageId) {
        return ResponseEntity.ok(guideLanguageService.updateLanguageAssignment(id, guideId, languageId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable Long id) {
        guideLanguageService.removeLanguage(id);
        return ResponseEntity.noContent().build();
    }
}