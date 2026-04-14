package com.ecotravel.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GearRequirementDTO {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "Naziv opreme je obavezan")
    private String itemName;

    @NotNull(message = "Morate naznačiti da li je oprema obavezna")
    private Boolean isMandatory;

    @NotNull(message = "ID avanture je obavezan")
    private Long adventureId;
}