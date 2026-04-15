package com.ecotravel.accommodation_service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponseDTO {
    private String message;
    private String traceId;
    private int status;
    private LocalDateTime timestamp;
    private Object details;

    public ErrorResponseDTO(String message, String traceId, int status) {
        this.message = message;
        this.traceId = traceId;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }
}