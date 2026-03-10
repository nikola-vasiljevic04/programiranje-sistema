package com.ecotravel.accommodation_service.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
public class AddressRequestDTO {
    @NotBlank(message = "Grad je obavezan")
    private String city;

    @NotBlank(message = "Poštanski broj je obavezan")
    @Size(min = 5, max = 10, message = "Poštanski broj mora biti između 5 i 10 karaktera")
    private String zipCode;

    @NotBlank(message = "Ulica i broj su obavezni")
    private String streetAddress;
}
