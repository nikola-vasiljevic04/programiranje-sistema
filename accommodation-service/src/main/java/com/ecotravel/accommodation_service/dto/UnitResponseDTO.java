package com.ecotravel.accommodation_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "Response DTO koji prikazuje kompletne detalje smeštajne jedinice, uključujući izračunatu trenutnu cenu")
@Data
public class UnitResponseDTO {

    @Schema(description = "Jedinstveni ID smeštajne jedinice", example = "1")
    private Long id;

    @Schema(description = "Naziv smeštaja", example = "Planinska Idila")
    private String name;

    @Schema(description = "Tip jedinice (npr. Koliba, Šator, Kuća)", example = "Koliba")
    private String unitType;

    @Schema(description = "Osnovna cena po noćenju", example = "120.0")
    private Double basePricePerNight;

    @Schema(description = "Detaljne informacije o adresi i lokaciji")
    private AddressResponseDTO address;

    @Schema(description = "Trenutna cena nakon primene sezonskih množitelja i dodataka", example = "195.0")
    private Double calculatedPrice;

    @Schema(description = "Lista dostupnih sadržaja u ovom smeštaju")
    private List<FacilityDTO> facilities;

    @Schema(description = "Lista sezonskih cenovnika koji se primenjuju na ovaj smeštaj")
    private List<PriceTierDTO> priceTiers;
}
