package com.ecotravel.accommodation_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;
@Schema(description = "Podaci potrebni za kreiranje ili ažuriranje adrese smeštaja")
@Data
public class AddressRequestDTO {
    @Schema(description = "Grad u kojem se nalazi smeštaj", example = "Beograd")
    @NotBlank(message = "Grad je obavezan")
    private String city;
    @Schema(description = "Poštanski broj (5 do 10 karaktera)", example = "11000")
    @NotBlank(message = "Poštanski broj je obavezan")
    @Size(min = 5, max = 10, message = "Poštanski broj mora biti između 5 i 10 karaktera")
    private String zipCode;
    @Schema(description = "Ulica i broj smeštajnog objekta", example = "Danijelova 32")
    @NotBlank(message = "Ulica i broj su obavezni")
    private String streetAddress;
}
