package com.ecotravel.maintenance_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "Zahtev za kreiranje novog radnog naloga")
public class WorkOrderRequestDTO {

    @NotNull(message = "ID smeštajne jedinice je obavezan")
    @Schema(description = "ID smeštaja iz Accommodation servisa", example = "1")
    private Long unitId;

    @NotNull(message = "ID radnika je obavezan")
    @Schema(example = "1")
    private Long workerId;

    @NotNull(message = "ID zadatka je obavezan")
    @Schema(example = "2")
    private Long taskId;

    @FutureOrPresent(message = "Datum zakazivanja ne može biti u prošlosti")
    @NotNull(message = "Datum je obavezan")
    @Schema(description = "Kada zadatak treba da se izvrši", example = "2026-04-10T10:00:00")
    private LocalDateTime scheduledFor;
}