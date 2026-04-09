package com.ecotravel.controller;

import com.ecotravel.dtos.GuideProfileRequestDTO;
import com.ecotravel.dtos.GuideProfileResponseDTO;
import com.ecotravel.service.GuideProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/guide-profiles") // Promenjen path da bude precizniji
@RequiredArgsConstructor
public class GuideProfileController {

    private final GuideProfileService guideProfileService;

    @GetMapping
    public List<GuideProfileResponseDTO> getAll() {
        return guideProfileService.getAllProfiles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GuideProfileResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(guideProfileService.getProfileById(id));
    }

    @PostMapping
    public ResponseEntity<GuideProfileResponseDTO> create(@Valid @RequestBody GuideProfileRequestDTO dto) {
        return new ResponseEntity<>(guideProfileService.createProfile(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GuideProfileResponseDTO> update(@PathVariable Long id, @Valid @RequestBody GuideProfileRequestDTO dto) {
        return ResponseEntity.ok(guideProfileService.updateProfile(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        guideProfileService.deleteProfile(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/completeness")
    public ResponseEntity<Double> getCompleteness(@PathVariable Long id) {
        return ResponseEntity.ok(guideProfileService.calculateCompleteness(id));
    }
}