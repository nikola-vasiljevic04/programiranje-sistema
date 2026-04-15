package com.ecotravel.exception;

import com.ecotravel.dtos.ErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private String getTraceId(HttpServletRequest request) {
        String traceId = request.getHeader("X-Trace-ID");
        return traceId != null ? traceId : "N/A";
    }

    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<ErrorResponseDTO> handleServiceUnavailable(ServiceUnavailableException ex, HttpServletRequest request) {
        return buildResponse(ex.getMessage(), getTraceId(request));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleAllExceptions(Exception ex, HttpServletRequest request) {
        ErrorResponseDTO errorDTO = new ErrorResponseDTO("An unexpected finance error occurred", getTraceId(request), HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorDTO.setDetails(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
    }

    private ResponseEntity<ErrorResponseDTO> buildResponse(String message, String traceId) {
        ErrorResponseDTO error = new ErrorResponseDTO(message, traceId, HttpStatus.SERVICE_UNAVAILABLE.value());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(error);
    }
}