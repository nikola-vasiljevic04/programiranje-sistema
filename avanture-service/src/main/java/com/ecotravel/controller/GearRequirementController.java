package com.ecotravel.controller;

import com.ecotravel.dtos.GearRequirementDTO;
import com.ecotravel.service.GearRequirementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gear")
@RequiredArgsConstructor
public class GearRequirementController {

    private final GearRequirementService gearService;

    @GetMapping
    public ResponseEntity<List<GearRequirementDTO>> getAll() {
        return ResponseEntity.ok(gearService.getAllGear());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GearRequirementDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(gearService.getGearById(id));
    }

    @GetMapping("/adventure/{adventureId}")
    public ResponseEntity<List<GearRequirementDTO>> getByAdventure(@PathVariable Long adventureId) {
        return ResponseEntity.ok(gearService.getGearByAdventure(adventureId));
    }

    @PostMapping
    public ResponseEntity<GearRequirementDTO> addGear(@Valid @RequestBody GearRequirementDTO dto) {
        return new ResponseEntity<>(gearService.addGearToAdventure(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GearRequirementDTO> updateGear(
            @PathVariable Long id,
            @Valid @RequestBody GearRequirementDTO dto) {
        return ResponseEntity.ok(gearService.updateGear(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGear(@PathVariable Long id) {
        gearService.deleteGear(id);
        return ResponseEntity.noContent().build();
    }
}