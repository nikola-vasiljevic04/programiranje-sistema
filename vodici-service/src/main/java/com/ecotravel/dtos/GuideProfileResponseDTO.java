package com.ecotravel.dtos;

import lombok.Data;
import java.util.List;

@Data
public class GuideProfileResponseDTO {
    private Long id;
    private String fullName; // Ime + Prezime (spaja Mapper)
    private String bio;
    private Double rating;
    private String contactEmail;
    private Double completeness;

    // Mapper ovde ubacuje nazive jezika (npr. ["Engleski", "Italijanski"])
    private List<String> languages;

    private List<CertificationResponseDTO> certifications;
}