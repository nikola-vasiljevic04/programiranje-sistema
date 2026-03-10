package com.ecotravel.accommodation_service.controller;

import com.ecotravel.accommodation_service.dto.UnitRequestDTO;
import com.ecotravel.accommodation_service.dto.UnitResponseDTO;
import com.ecotravel.accommodation_service.service.UnitService;
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
public class UnitController {

    private final UnitService unitService;

    // 1. Dobavljanje svih smeštajnih jedinica (vraća listu sa ID-jevima)
    @GetMapping
    public ResponseEntity<List<UnitResponseDTO>> getAllUnits() {
        return ResponseEntity.ok(unitService.getAllUnits());
    }

    // 2. Dobavljanje jedne jedinice po ID-ju
    @GetMapping("/{id}")
    public ResponseEntity<UnitResponseDTO> getUnitById(@PathVariable Long id) {
        return ResponseEntity.ok(unitService.getUnitById(id));
    }

    // 3. Kreiranje nove jedinice (prima Request bez ID-ja, vraća Response sa ID-jem)
    @PostMapping
    public ResponseEntity<UnitResponseDTO> createUnit(@Valid @RequestBody UnitRequestDTO unitRequestDTO) {
        // Servis sada prihvata RequestDTO i vraća ResponseDTO
        UnitResponseDTO savedUnit = unitService.createUnit(unitRequestDTO);
        return new ResponseEntity<>(savedUnit, HttpStatus.CREATED);
    }
    @GetMapping("/{id}/check-availability")
    public ResponseEntity<Boolean> checkAvailability(
            @PathVariable Long id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut) {

        // Pozivamo servis koji smo popravili da koristi instancu repozitorijuma
        boolean isAvailable = unitService.isUnitAvailable(id, checkIn, checkOut);

        return ResponseEntity.ok(isAvailable);
    }
}