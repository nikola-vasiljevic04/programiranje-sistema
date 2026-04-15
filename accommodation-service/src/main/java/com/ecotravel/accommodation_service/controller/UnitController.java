package com.ecotravel.accommodation_service.controller;

import com.ecotravel.accommodation_service.dto.UnitRequestDTO;
import com.ecotravel.accommodation_service.dto.UnitResponseDTO;
import com.ecotravel.accommodation_service.service.UnitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/units")
@RequiredArgsConstructor
@Tag(name = "Accommodation Unit Controller", description = "Upravljanje smeštajnim jedinicama i kalkulacija cena")
public class UnitController {
    private final UnitService unitService;
    @GetMapping
    @Operation(summary = "Izlistaj sve smeštaje", description = "Vraća listu svih smeštaja. Opciono filtriranje po tipu (npr. Koliba)")
    public ResponseEntity<List<UnitResponseDTO>> getAllUnits(@RequestParam(required = false) String type) {
        return ResponseEntity.ok(unitService.getAllUnits());
    }
    @GetMapping("/{id}")
    @Operation(summary = "Pronađi smeštaj po ID-u")
    public ResponseEntity<UnitResponseDTO> getUnitById(@PathVariable Long id) {
        return ResponseEntity.ok(unitService.getUnitById(id));
    }
    @PostMapping
    @Operation(summary = "Kreiraj novi smeštaj", description = "Dodaje novi smeštaj sa adresom i listom ID-jeva sadržaja")
    public ResponseEntity<UnitResponseDTO> createUnit(@Valid @RequestBody UnitRequestDTO unitRequestDTO) {
        UnitResponseDTO savedUnit = unitService.createUnit(unitRequestDTO);
        return new ResponseEntity<>(savedUnit, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    @Operation(summary = "Ažuriraj postojeći smeštaj", description = "Menja podatke o smeštaju na osnovu ID-a")
    public ResponseEntity<UnitResponseDTO> updateUnit(@PathVariable Long id, @Valid @RequestBody UnitRequestDTO dto) {
        return ResponseEntity.ok(unitService.updateUnit(id, dto));
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Obriši smeštaj", description = "Trajno uklanja smeštajnu jedinicu iz baze")
    public ResponseEntity<Void> deleteUnit(@PathVariable Long id) {
        unitService.deleteUnit(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}/check-availability")
    @Operation(summary = "Proveri dostupnost", description = "Proverava da li je smeštaj slobodan u zadatom periodu")
    public ResponseEntity<Boolean> checkAvailability(
            @PathVariable Long id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut) {

        boolean isAvailable = unitService.isUnitAvailable(id, checkIn, checkOut);
        return ResponseEntity.ok(isAvailable);
    }
    @GetMapping("/{id}/price")
    @Operation(summary = "Izračunaj trenutnu cenu", description = "Vraća cenu sa uračunatim sezonskim množiteljima i dodatnim troškovima sadržaja")
    public ResponseEntity<Double> getCurrentPrice(@PathVariable Long id) {
        return ResponseEntity.ok(unitService.getUnitById(id).getCalculatedPrice());
    }
}