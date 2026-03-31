package com.ecotravel.accommodation_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Schema(description = "DTO koji prikazuje period u kojem je smeštajna jedinica zauzeta ili rezervisana")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailabilityDTO {

    @Schema(description = "Datum početka zauzetosti", example = "2026-06-15")
    private LocalDate startDate;

    @Schema(description = "Datum završetka zauzetosti", example = "2026-06-25")
    private LocalDate endDate;

    @Schema(description = "Razlog zauzetosti (npr. Rezervacija, Renoviranje, Godišnji odmor)", example = "Rezervacija #456")
    private String reason;
}
