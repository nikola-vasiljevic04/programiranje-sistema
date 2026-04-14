package com.ecotravel.controller;

import com.ecotravel.dtos.TimeSlotRequestDTO;
import com.ecotravel.dtos.TimeSlotResponseDTO;
import com.ecotravel.service.TimeSlotService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/time-slots")
@RequiredArgsConstructor
public class TimeSlotController {

    private final TimeSlotService timeSlotService;

    @PostMapping
    public ResponseEntity<TimeSlotResponseDTO> create(@Valid @RequestBody TimeSlotRequestDTO dto) {
        return ResponseEntity.ok(timeSlotService.createTimeSlot(dto));
    }

    @GetMapping("/adventure/{adventureId}")
    public ResponseEntity<List<TimeSlotResponseDTO>> getByAdventure(@PathVariable Long adventureId) {
        return ResponseEntity.ok(timeSlotService.getSlotsByAdventure(adventureId));
    }

    @PostMapping("/{id}/book")
    public ResponseEntity<String> book(@PathVariable Long id, @RequestParam int num) {
        boolean success = timeSlotService.bookPlace(id, num);
        if (success) {
            return ResponseEntity.ok("Uspešno rezervisano " + num + " mesta.");
        } else {
            return ResponseEntity.badRequest().body("Nema dovoljno slobodnih mesta!");
        }
    }


    @GetMapping
    public ResponseEntity<List<TimeSlotResponseDTO>> getAll() {
        return ResponseEntity.ok(timeSlotService.getAllTimeSlots());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TimeSlotResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(timeSlotService.getTimeSlotById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TimeSlotResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody TimeSlotRequestDTO dto) {
        return ResponseEntity.ok(timeSlotService.updateTimeSlot(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        timeSlotService.deleteTimeSlot(id);
        return ResponseEntity.noContent().build();
    }
}