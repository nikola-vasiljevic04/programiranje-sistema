package com.ecotravel.dtos;

import lombok.Data;
import java.util.List;

@Data
public class GuideProfileRequestDTO {
    private String firstName;
    private String lastName;
    private String bio;
    private String contactEmail;
    private List<Long> languageIds;
}