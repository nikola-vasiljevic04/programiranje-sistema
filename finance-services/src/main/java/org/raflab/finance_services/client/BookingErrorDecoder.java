package org.raflab.finance_services.client;

import feign.Response;
import feign.codec.ErrorDecoder;
import jakarta.persistence.EntityNotFoundException;

public class BookingErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == 404) {
            return new EntityNotFoundException("Booking not found");
        }
        return new Default().decode(methodKey, response);
    }
}