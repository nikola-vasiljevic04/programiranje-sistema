package com.ecotravel.dtos;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdventureReviewRequestDTO {
    @Min(1) @Max(5)
    private Integer rating;
    private String comment;
    private Long adventureId;
}