package com.ecotravel.maintenance_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Zahtev za kreiranje zadatka održavanja")
public class MaintenanceTaskRequestDTO {
    @NotBlank(message = "Naziv zadatka je obavezan")
    @Schema(example = "Popravka klime")
    private String taskName;

    @NotNull(message = "Procenjeno vreme trajanja je obavezno")
    @Min(value = 1, message = "Zadatak mora trajati bar 1 sat")
    @Schema(example = "2")
    private Integer estimatedDurationHours;
}