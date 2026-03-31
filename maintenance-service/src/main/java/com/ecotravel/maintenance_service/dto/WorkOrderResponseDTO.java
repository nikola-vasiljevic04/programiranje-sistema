package com.ecotravel.maintenance_service.dto;

import com.ecotravel.maintenance_service.model.WorkOrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "Prikaz radnog naloga sa detaljima")
public class WorkOrderResponseDTO {
    private Long id;
    private Long unitId;
    private Long workerId;
    private String workerFullName;
    private String taskName;
    private Integer estimatedDurationHours;
    private WorkOrderStatus status;
    private LocalDateTime scheduledFor;
    private LocalDateTime completionDate;
}