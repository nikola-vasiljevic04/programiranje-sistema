package com.ecotravel.accommodation_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description = "Zahtev za dodavanje perioda zauzetosti")
public class AvailabilityRequestDTO {
    @Schema(example = "2026-06-10")
    @NotNull(message = "Datum početka je obavezan")
    private LocalDate startDate;

    @Schema(example = "2026-06-15")
    @NotNull(message = "Datum završetka je obavezan")
    private LocalDate endDate;

    @Schema(example = "Rezervacija #123")
    @NotBlank(message = "Razlog mora biti naveden")
    private String reason;

    @Schema(example = "1")
    @NotNull(message = "ID smeštaja je obavezan")
    private Long unitId;
}