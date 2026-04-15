package com.ecotravel.accommodation_service.controller;

import com.ecotravel.accommodation_service.dto.UnitResponseDTO;
import com.ecotravel.accommodation_service.service.UnitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
@Tag(name = "Analytics", description = "slozeni upiti nad bazom za 2. zadatak")
public class UnitSearchController {
    private final UnitService unitService;
    @GetMapping("/search")
    @Operation(summary = "Pretraga po tipu smestaja i gradu")
    public ResponseEntity<List<UnitResponseDTO>> searchByTypeAndCity(
            @RequestParam String type,
            @RequestParam String city) {
        return ResponseEntity.ok(unitService.findByTypeAndCity(type, city));
    }
    @GetMapping("/average-price")
    @Operation(summary = "Prosecna cena za tip smestajaa")
    public ResponseEntity<Double> getAveragePrice(@RequestParam String type) {
        return ResponseEntity.ok(unitService.getAveragePriceByType(type));
    }
    @GetMapping("/by-facility")
    @Operation(summary = "Pronalazi sve smestaje sa odredjenim sadrzajem")
    public ResponseEntity<List<UnitResponseDTO>> searchByFacility(
            @RequestParam String facilityName) {
        return ResponseEntity.ok(unitService.findByFacility(facilityName));
    }

}
