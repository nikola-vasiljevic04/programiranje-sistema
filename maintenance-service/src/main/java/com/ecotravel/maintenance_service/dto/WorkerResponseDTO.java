package com.ecotravel.maintenance_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Odgovor sa podacima o radniku")
public class WorkerResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String specialty;
}