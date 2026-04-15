package org.raflab.authservice.errors;

import jakarta.servlet.http.HttpServletRequest;
import org.raflab.authservice.dto.ErrorResponseDTO;
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

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleNotFound(NotFoundException ex, HttpServletRequest request) {
        return buildResponse(ex.getMessage(), getTraceId(request), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotAuthorizedException.class)
    public ResponseEntity<ErrorResponseDTO> handleNotAuthorized(NotAuthorizedException ex, HttpServletRequest request) {
        return buildResponse(ex.getMessage(), getTraceId(request), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponseDTO> handleForbidden(ForbiddenException ex, HttpServletRequest request) {
        return buildResponse(ex.getMessage(), getTraceId(request), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponseDTO> handleConflict(ConflictException ex, HttpServletRequest request) {
        return buildResponse(ex.getMessage(), getTraceId(request), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleAllExceptions(Exception ex, HttpServletRequest request) {
        ErrorResponseDTO errorDTO = new ErrorResponseDTO(
                "An unexpected auth error occurred",
                getTraceId(request),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        errorDTO.setDetails(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
    }

    private ResponseEntity<ErrorResponseDTO> buildResponse(String message, String traceId, HttpStatus status) {
        ErrorResponseDTO error = new ErrorResponseDTO(message, traceId, status.value());
        return ResponseEntity.status(status).body(error);
    }
}