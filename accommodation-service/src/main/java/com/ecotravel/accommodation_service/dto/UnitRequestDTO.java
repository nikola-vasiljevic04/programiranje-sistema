package com.ecotravel.accommodation_service.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class UnitRequestDTO {
    @NotBlank(message = "Naziv smestaja je obavezan")
    private String name;

    @NotBlank(message = "Tip jedinice je obavezan")
    private String unitType;

    @NotNull(message = "Osnovna cena mora biti definisana")
    @Min(value = 0, message = "Cena ne moze biti negativna")
    private Double basePricePerNight;

    @NotNull(message = "Adresa je obavezna")
    @Valid // Proverava AddressRequestDTO polja
    private AddressRequestDTO address;

    @NotEmpty(message = "Morate odabrati bar jedan sadrzaj")
    private List<Long> facilityIds;
}