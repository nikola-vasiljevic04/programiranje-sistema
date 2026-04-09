package com.ecotravel.dtos;

import lombok.Data;

@Data
public class GuideLanguageDTO {
    private Long id;
    private Long guideId;
    private String guideName;    // Opciono, za lepši prikaz
    private Long languageId;
    private String languageName; // Opciono, npr. "Engleski"
}