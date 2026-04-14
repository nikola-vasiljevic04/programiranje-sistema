package com.ecotravel.dtos;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdventureRequestDTO {
    @NotBlank(message = "Naslov je obavezan")
    private String title;

    private String description;

    @NotBlank(message = "Težina je obavezna")
    private String difficultyLevel;

    @Positive(message = "Cena mora biti pozitivan broj")
    private Double basePrice;

    @NotNull(message = "Morate izabrati kategoriju")
    private Long categoryId;

    private Long guideId;
}