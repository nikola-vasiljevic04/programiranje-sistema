package com.ecotravel.exception;

import com.ecotravel.dtos.ErrorResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    private String getTraceId() {
        String traceId = MDC.get("X-Trace-ID");
        return traceId != null ? traceId : "N/A";
    }

    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<ErrorResponseDTO> handleServiceUnavailable(ServiceUnavailableException ex) {
        return buildResponse(ex.getMessage(), getTraceId());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleAllExceptions(Exception ex) {
        ErrorResponseDTO errorDTO = new ErrorResponseDTO(
                "An unexpected auth error occurred",
                getTraceId(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        log.error("Adventure-Service Error: {}", ex.getMessage());
        errorDTO.setDetails(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
    }

    private ResponseEntity<ErrorResponseDTO> buildResponse(String message, String traceId) {
        ErrorResponseDTO error = new ErrorResponseDTO(message, traceId, HttpStatus.SERVICE_UNAVAILABLE.value());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(error);
    }
}