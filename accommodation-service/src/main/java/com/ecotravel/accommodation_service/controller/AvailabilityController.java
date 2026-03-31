package com.ecotravel.accommodation_service.controller;

import com.ecotravel.accommodation_service.dto.AvailabilityDTO;
import com.ecotravel.accommodation_service.dto.AvailabilityRequestDTO;
import com.ecotravel.accommodation_service.service.AvailabilityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/availability")
@RequiredArgsConstructor
@Tag(name = "Availability Controller", description = "Upravljanje kalendarom zauzetosti i rezervacijama")
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    @PostMapping
    @Operation(summary = "Dodaj novi termin zauzetosti",
            description = "Kreira zapis u kalendaru. Sistem automatski proverava da li se termin preklapa sa postojećim.")
    public ResponseEntity<String> add(@Valid @RequestBody AvailabilityRequestDTO dto) {
        availabilityService.addAvailability(dto);
        return ResponseEntity.ok("Termin je uspešno rezervisan u kalendaru.");
    }

    @GetMapping("/unit/{unitId}")
    @Operation(summary = "Prikaži sve zauzete termine za smeštaj",
            description = "Vraća listu svih rezervisanih perioda za prosleđeni ID smeštaja.")
    public ResponseEntity<List<AvailabilityDTO>> getByUnit(@PathVariable Long unitId) {
        return ResponseEntity.ok(availabilityService.getAvailabilityForUnit(unitId));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Obriši termin iz kalendara",
            description = "Uklanja zapis o zauzetosti na osnovu ID-a zapisa.")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        availabilityService.deleteAvailability(id);
        return ResponseEntity.noContent().build();
    }
}