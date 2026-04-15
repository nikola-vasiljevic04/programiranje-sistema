package com.ecotravel.maintenance_service.client;

import feign.Response;
import feign.codec.ErrorDecoder;
import jakarta.persistence.EntityNotFoundException;

public class UnitErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == 404) {
            return new EntityNotFoundException("Smeštajna jedinica nije pronađena u accommodation-service-u.");
        }
        return new Default().decode(methodKey, response);
    }
}
