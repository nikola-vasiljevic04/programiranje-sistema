package com.ecotravel.dtos;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertificationResponseDTO {
    private Long id;
    private String name;
    private LocalDate expiryDate;
    private String issuingBody; // Dodajemo ovo polje
    private boolean valid;
}