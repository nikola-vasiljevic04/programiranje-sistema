package com.ecotravel.client.dto;

import lombok.Data;

import java.util.List;

@Data
public class GuideProfileResponseDTO {
    private Long id;
    private String fullName;
    private String bio;
    private Double rating;
    private String contactEmail;

    private List<String> languages;
}