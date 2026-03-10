package com.ecotravel.accommodation_service.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceTierDTO {
    private Long id;
    private String name;
    private Double multiplier;
    private LocalDate startDate;
    private LocalDate endDate;
}
