package com.ecotravel.maintenance_service.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Zahtev za kreiranje ili izmenu radnika")
public class WorkerRequestDTO {
    @NotBlank(message = "Ime radnika je obavezno")
    @Schema(example = "Marko")
    private String firstName;

    @NotBlank(message = "Prezime radnika je obavezno")
    @Schema(example = "Marković")
    private String lastName;

    @NotBlank(message = "Specijalnost je obavezna")
    @Schema(example = "Električar")
    private String specialty;
}