package com.ecotravel.maintenance_service.controller;

import com.ecotravel.maintenance_service.dto.WorkerRequestDTO;
import com.ecotravel.maintenance_service.dto.WorkerResponseDTO;
import com.ecotravel.maintenance_service.service.WorkerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workers")
@Tag(name = "Worker Controller", description = "Upravljanje zaposlenima")
public class WorkerController {

    private final WorkerService workerService;

    public WorkerController(WorkerService workerService) {
        this.workerService = workerService;
    }

    @GetMapping
    @Operation(summary = "Prikaži sve radnike")
    public ResponseEntity<List<WorkerResponseDTO>> getAll() {
        return ResponseEntity.ok(workerService.getAllWorkers());
    }

    @PostMapping
    @Operation(summary = "Dodaj novog radnika")
    public ResponseEntity<WorkerResponseDTO> create(@Valid @RequestBody WorkerRequestDTO dto) {
        return new ResponseEntity<>(workerService.createWorker(dto), HttpStatus.CREATED);
    }
}