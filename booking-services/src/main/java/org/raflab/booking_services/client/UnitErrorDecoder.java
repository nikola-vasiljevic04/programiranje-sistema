package org.raflab.booking_services.client;

import feign.Response;
import feign.codec.ErrorDecoder;
import jakarta.persistence.EntityNotFoundException;

public class UnitErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == 404) {
            return new EntityNotFoundException("Unit not found");
        }
        return new Default().decode(methodKey, response);
    }
}