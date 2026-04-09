package com.ecotravel.dtos;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertificationRequestDTO {

    @NotNull(message = "ID vodiča je obavezan")
    private Long guideId;

    @NotBlank(message = "Naziv sertifikata je obavezan")
    private String name;

    @NotBlank(message = "Institucija koja izdaje sertifikat je obavezna")
    private String issuingBody;

    @NotNull(message = "Datum isteka je obavezan")
    @FutureOrPresent(message = "Datum isteka ne može biti u prošlosti")
    private LocalDate expiryDate;
}