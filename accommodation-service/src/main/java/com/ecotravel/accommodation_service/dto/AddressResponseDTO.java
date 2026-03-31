package com.ecotravel.accommodation_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
@Schema(description = "Response DTO koji sadrži podatke o adresi sa jedinstvenim identifikatorom")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponseDTO {
    @Schema(description = "Jedinstveni ID adrese u bazi podataka", example = "1")
    private Long id;

    @Schema(description = "Grad u kojem se nalazi smeštaj", example = "Zlatibor")
    private String city;

    @Schema(description = "Poštanski broj lokacije", example = "31315")
    private String zipCode;

    @Schema(description = "Ulica i broj", example = "Kraljeve Vode bb")
    private String streetAddress;
}
