package com.ecotravel.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Data
 @NoArgsConstructor
 @AllArgsConstructor
public class AdventureReviewResponseDTO {
    private Long id;
    private Integer rating;
    private String comment;
    private String adventureTitle;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt;
}