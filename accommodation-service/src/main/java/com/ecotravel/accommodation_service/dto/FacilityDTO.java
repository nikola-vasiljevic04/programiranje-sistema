package com.ecotravel.accommodation_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Schema(description = "DTO koji predstavlja dodatni sadržaj smeštaja (npr. Bazen, WiFi, Sauna)")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacilityDTO {

    @Schema(description = "Jedinstveni ID sadržaja", example = "1")
    private Long id;

    @Schema(description = "Naziv sadržaja", example = "Bazen")
    @NotBlank(message = "Naziv sadržaja je obavezan")
    private String name;

    @Schema(description = "Dodatni trošak koji ovaj sadržaj dodaje na osnovnu cenu", example = "25.0")
    @PositiveOrZero(message = "Dodatni trošak mora biti pozitivan broj ili nula")
    private Double extraCost;
}
