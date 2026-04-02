package com.ecotravel.maintenance_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "Istorija popravki za smeštajnu jedinicu")
public class MaintenanceLogResponseDTO {
    private Long id;
    private Long unitId;
    private String taskDescription;
    private String workerName;
    private LocalDateTime completedAt;
    private String notes;
}