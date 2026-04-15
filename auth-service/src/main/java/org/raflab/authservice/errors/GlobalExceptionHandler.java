package org.raflab.authservice.errors;

import lombok.extern.slf4j.Slf4j;
import org.raflab.authservice.dto.ErrorResponseDTO;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    private String getTraceId() {
        String traceId = MDC.get("traceId");
        return traceId != null ? traceId : "N/A";
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleNotFound(NotFoundException ex) {
        log.debug("Resource not found: {}", ex.getMessage());
        return buildResponse(ex.getMessage(), getTraceId(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotAuthorizedException.class)
    public ResponseEntity<ErrorResponseDTO> handleNotAuthorized(NotAuthorizedException ex) {
        log.debug("Unauthorized access: {}", ex.getMessage());
        return buildResponse(ex.getMessage(), getTraceId(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponseDTO> handleForbidden(ForbiddenException ex) {
        log.warn("Forbidden access attempt!");
        return buildResponse(ex.getMessage(), getTraceId(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponseDTO> handleConflict(ConflictException ex) {
        log.warn("Conflict detected: {}", ex.getMessage());
        return buildResponse(ex.getMessage(), getTraceId(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleAllExceptions(Exception ex) {
        ErrorResponseDTO errorDTO = new ErrorResponseDTO(
                "An unexpected auth error occurred",
                getTraceId(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        log.error("Auth-Service Error: {}", ex.getMessage());
        errorDTO.setDetails(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
    }

    private ResponseEntity<ErrorResponseDTO> buildResponse(String message, String traceId, HttpStatus status) {
        ErrorResponseDTO error = new ErrorResponseDTO(message, traceId, status.value());
        return ResponseEntity.status(status).body(error);
    }
}