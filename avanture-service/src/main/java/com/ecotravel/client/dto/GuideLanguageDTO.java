package com.ecotravel.client.dto;

import lombok.Data;

@Data
public class GuideLanguageDTO {
    private Long id;
    private Long guideId;
    private String guideName;
    private Long languageId;
    private String languageName;
}