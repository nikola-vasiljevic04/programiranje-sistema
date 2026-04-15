package com.ecotravel.accommodation_service.exception;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class GlobalFeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        HttpStatus status = HttpStatus.valueOf(response.status());

        return switch (status) {
            case NOT_FOUND -> 
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Downstream service: Resource not found");
            case UNAUTHORIZED -> 
                new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Downstream service: Authentication failed");
            case FORBIDDEN -> 
                new ResponseStatusException(HttpStatus.FORBIDDEN, "Downstream service: Access forbidden");
            case TOO_MANY_REQUESTS -> 
                new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "Downstream service: Rate limit exceeded");
            case BAD_REQUEST -> 
                new ResponseStatusException(HttpStatus.BAD_REQUEST, "Downstream service: Invalid request sent");
            case CONFLICT -> 
                new ResponseStatusException(HttpStatus.CONFLICT, "Downstream service: Resource conflict");
            default -> 
                new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Communication error with downstream service: " + status.value());
        };
    }
}