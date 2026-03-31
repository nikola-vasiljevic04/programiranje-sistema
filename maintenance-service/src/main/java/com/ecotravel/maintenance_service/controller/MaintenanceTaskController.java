package com.ecotravel.maintenance_service.controller;

import com.ecotravel.maintenance_service.dto.MaintenanceTaskRequestDTO;
import com.ecotravel.maintenance_service.dto.MaintenanceTaskResponseDTO;
import com.ecotravel.maintenance_service.service.MaintenanceTaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@Tag(name = "Maintenance Task Controller", description = "Upravljanje zadacima održavanja")
public class MaintenanceTaskController {

    private final MaintenanceTaskService taskService;

    public MaintenanceTaskController(MaintenanceTaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    @Operation(summary = "Prikaži sve vrste zadataka")
    public ResponseEntity<List<MaintenanceTaskResponseDTO>> getAll() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @PostMapping
    @Operation(summary = "Dodaj novi zadatak")
    public ResponseEntity<MaintenanceTaskResponseDTO> create(@Valid @RequestBody MaintenanceTaskRequestDTO dto) {
        return new ResponseEntity<>(taskService.createTask(dto), HttpStatus.CREATED);
    }
}