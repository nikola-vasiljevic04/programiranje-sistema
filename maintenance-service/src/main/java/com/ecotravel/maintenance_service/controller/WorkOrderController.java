package com.ecotravel.maintenance_service.controller;

import com.ecotravel.maintenance_service.dto.WorkOrderRequestDTO;
import com.ecotravel.maintenance_service.dto.WorkOrderResponseDTO;
import com.ecotravel.maintenance_service.model.WorkOrderStatus;
import com.ecotravel.maintenance_service.service.WorkOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/work-orders")
@Tag(name = "Work Order Controller", description = "Upravljanje radnim nalozima i održavanjem")
public class WorkOrderController {

    private final WorkOrderService workOrderService;

    public WorkOrderController(WorkOrderService workOrderService) {
        this.workOrderService = workOrderService;
    }
    @GetMapping("/{id}")
    @Operation(summary = "Pronađi radni nalog", description = "Vraća detalje radnog naloga na osnovu ID-ja")
    public ResponseEntity<WorkOrderResponseDTO> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(workOrderService.getWorkOrderById(id));
    }
    // 1. Kreiranje (CRUD - Create)
    @PostMapping
    @Operation(summary = "Kreiraj novi radni nalog", description = "Zakaži održavanje za određenu smeštajnu jedinicu")
    public ResponseEntity<WorkOrderResponseDTO> createOrder(@Valid @RequestBody WorkOrderRequestDTO dto) {
        return new ResponseEntity<>(workOrderService.createWorkOrder(dto), HttpStatus.CREATED);
    }

    // 2. Dobavljanje svih sa FILTRIRANJEM (Uslov za odbranu)
    @GetMapping
    @Operation(summary = "Izlistaj radne naloge", description = "Vraća sve naloge, uz mogućnost filtriranja po statusu (PENDING, IN_PROGRESS, COMPLETED)")
    public ResponseEntity<List<WorkOrderResponseDTO>> getAllOrders(
            @RequestParam(required = false) WorkOrderStatus status) {
        return ResponseEntity.ok(workOrderService.getWorkOrders(status));
    }

    // 3. Završetak naloga (Poslovna logika)
    @PutMapping("/{id}/complete")
    @Operation(summary = "Završi radni nalog", description = "Menja status naloga u COMPLETED i kreira zapis u istoriji održavanja (MaintenanceLog)")
    public ResponseEntity<WorkOrderResponseDTO> completeOrder(
            @PathVariable Long id,
            @RequestParam(required = false) String notes) {
        return ResponseEntity.ok(workOrderService.completeWorkOrder(id, notes));
    }

    // 4. MATEMATIČKA KALKULACIJA (Glavni uslov za odbranu)
    @GetMapping("/workers/{workerId}/workload")
    @Operation(summary = "Izračunaj opterećenje radnika", description = "Računa preostalo vreme u satima za sve aktivne zadatke prosleđenog radnika")
    public ResponseEntity<Integer> getWorkerWorkload(@PathVariable Long workerId) {
        return ResponseEntity.ok(workOrderService.calculateRemainingWorkloadForWorker(workerId));
    }

    // 5. Brisanje (CRUD - Delete)
    @DeleteMapping("/{id}")
    @Operation(summary = "Obriši radni nalog")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        // Implementiraćemo direktivno ovde za brzinu, ili možeš dodati u servis
        // workOrderService.deleteWorkOrder(id);
        return ResponseEntity.noContent().build();
    }
}