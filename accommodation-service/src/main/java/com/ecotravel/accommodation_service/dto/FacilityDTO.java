package com.ecotravel.accommodation_service.dto;

import jakarta.validation.constraints.Positive;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacilityDTO {
    private Long id;
    private String name;

    @Positive(message = "Dodatni trosak mora biti pozitivan broj")
    private Double extraCost;
}
