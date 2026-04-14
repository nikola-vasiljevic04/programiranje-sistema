package com.ecotravel.dtos;

import lombok.Data;
import java.util.List;

@Data
public class GuideProfileResponseDTO {
    private Long id;
    private String fullName;
    private String bio;
    private Double rating;
    private String contactEmail;
    private Double completeness;

    private List<String> languages;

    private List<CertificationResponseDTO> certifications;
}