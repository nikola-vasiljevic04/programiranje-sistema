package org.raflab.gatewayservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorResponseDTO {
    private LocalDateTime timestamp;
    private String message;
    private String traceId;
    private int status;

    public ErrorResponseDTO(String message, String traceId, int status) {
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.traceId = traceId;
        this.status = status;
    }

}