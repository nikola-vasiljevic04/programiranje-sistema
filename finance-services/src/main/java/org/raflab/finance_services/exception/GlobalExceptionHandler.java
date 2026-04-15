package org.raflab.finance_services.exception;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.raflab.finance_services.dto.ErrorResponseDTO;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    private String getTraceId() {
        String traceId = MDC.get("X-Trace-ID");
        return traceId != null ? traceId : "N/A";
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleNotFound(EntityNotFoundException ex) {
        return buildResponse(ex.getMessage(), getTraceId(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleDataIntegrity(org.springframework.dao.DataIntegrityViolationException ex) {
        return buildResponse("Finance data integrity error: " + ex.getMessage(), getTraceId(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        ErrorResponseDTO errorDTO = new ErrorResponseDTO("Validation failed", getTraceId(), HttpStatus.BAD_REQUEST.value());
        errorDTO.setDetails(errors);

        return ResponseEntity.badRequest().body(errorDTO);
    }

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<ErrorResponseDTO> handleBadRequest(RuntimeException ex) {
        return buildResponse(ex.getMessage(), getTraceId(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<ErrorResponseDTO> handleServiceUnavailable(ServiceUnavailableException ex) {
        return buildResponse(ex.getMessage(), getTraceId(), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleAllExceptions(Exception ex) {
        ErrorResponseDTO errorDTO = new ErrorResponseDTO("An unexpected finance error occurred", getTraceId(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorDTO.setDetails(ex.getMessage());
        log.error("Finance-Service Error: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
    }

    private ResponseEntity<ErrorResponseDTO> buildResponse(String message, String traceId, HttpStatus status) {
        ErrorResponseDTO error = new ErrorResponseDTO(message, traceId, status.value());
        return ResponseEntity.status(status).body(error);
    }
}