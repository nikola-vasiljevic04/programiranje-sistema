package com.ecotravel.accommodation_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Schema(description = "DTO za kreiranje nove smeštajne jedinice sa adresom i sadržajima")
@Data
public class UnitRequestDTO {

    @Schema(description = "Jedinstveni naziv smeštaja", example = "Planinska Idila")
    @NotBlank(message = "Naziv smestaja je obavezan")
    private String name;

    @Schema(description = "Tip smeštajne jedinice", example = "Koliba")
    @NotBlank(message = "Tip jedinice je obavezan")
    private String unitType;

    @Schema(description = "Osnovna cena po noćenju bez dodataka", example = "120.0")
    @NotNull(message = "Osnovna cena mora biti definisana")
    @Min(value = 0, message = "Cena ne moze biti negativna")
    private Double basePricePerNight;

    @Schema(description = "Podaci o fizičkoj lokaciji smeštaja")
    @NotNull(message = "Adresa je obavezna")
    @Valid
    private AddressRequestDTO address;

    @Schema(description = "Lista ID-eva sadržaja koji su dostupni u smeštaju", example = "[1, 2, 4]")
    @NotEmpty(message = "Morate odabrati bar jedan sadrzaj")
    private List<Long> facilityIds;
}