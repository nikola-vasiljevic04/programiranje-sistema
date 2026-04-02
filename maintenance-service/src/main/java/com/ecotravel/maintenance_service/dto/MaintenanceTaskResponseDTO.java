package com.ecotravel.maintenance_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Odgovor sa podacima o zadatku")
public class MaintenanceTaskResponseDTO {
    private Long id;
    private String taskName;
    private Integer estimatedDurationHours;
}