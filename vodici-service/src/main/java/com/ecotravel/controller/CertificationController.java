package com.ecotravel.controller;

import com.ecotravel.dtos.CertificationRequestDTO;
import com.ecotravel.dtos.CertificationResponseDTO;
import com.ecotravel.service.CertificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/certifications")
@RequiredArgsConstructor
public class CertificationController {

    private final CertificationService service;

    @GetMapping
    public ResponseEntity<List<CertificationResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAllCertifications());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CertificationResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getCertificationById(id));
    }

    @GetMapping("/guide/{guideId}")
    public ResponseEntity<List<CertificationResponseDTO>> getByGuide(@PathVariable Long guideId) {
        return ResponseEntity.ok(service.getCertificationsByGuide(guideId));
    }

    @PostMapping
    public ResponseEntity<CertificationResponseDTO> add(@Valid @RequestBody CertificationRequestDTO dto) {
        return new ResponseEntity<>(service.addCertification(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CertificationResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody CertificationRequestDTO dto) {
        return ResponseEntity.ok(service.updateCertification(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteCertification(id);
        return ResponseEntity.noContent().build();
    }
}