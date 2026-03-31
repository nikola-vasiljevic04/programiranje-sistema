package com.ecotravel.accommodation_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Schema(description = "DTO koji definiše sezonske cene i množitelje za smeštajnu jedinicu")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceTierDTO {

    @Schema(description = "Jedinstveni ID cenovnika", example = "1")
    private Long id;

    @Schema(description = "Naziv sezone", example = "Zimska sezona 2026")
    @NotBlank(message = "Naziv sezone je obavezan")
    private String name;

    @Schema(description = "Množitelj cene (npr. 1.5 povećava cenu za 50%)", example = "1.5")
    @DecimalMin(value = "0.1", message = "Množitelj mora biti veći od 0")
    @NotNull(message = "Množitelj je obavezan")
    private Double multiplier;

    @Schema(description = "Datum početka važenja sezonske cene", example = "2026-12-01")
    @NotNull(message = "Datum početka je obavezan")
    private LocalDate startDate;

    @Schema(description = "Datum završetka važenja sezonske cene", example = "2027-02-28")
    @NotNull(message = "Datum završetka je obavezan")
    private LocalDate endDate;
}
