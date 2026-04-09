package com.ecotravel.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuideDTO {
    private Long id;
    private String fullName;
    private String contactEmail;
    private Double rating;
}