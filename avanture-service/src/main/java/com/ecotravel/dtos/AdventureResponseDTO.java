package com.ecotravel.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdventureResponseDTO {
    private Long id;
    private String title;
    private String description;
    private String difficultyLevel;
    private Double basePrice;
    private String categoryName;
    private GuideDTO guide;
}