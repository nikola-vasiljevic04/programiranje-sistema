package org.raflab.booking_services.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.raflab.booking_services.client.dto.AvailabilityRequestDTO;
import org.raflab.booking_services.exception.ServiceUnavailableException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AvailabilityClientService {
    private final AvailabilityClient availabilityClient;

    @CircuitBreaker(name = "unit-service", fallbackMethod = "addAvailabilityFallback")
    @Retry(name = "unit-service")
    public void addAvailability(AvailabilityRequestDTO dto) {
        availabilityClient.addAvailability(dto);
    }

    public void addAvailabilityFallback(AvailabilityRequestDTO dto, EntityNotFoundException e) {
        throw e;
    }

    public void addAvailabilityFallback(AvailabilityRequestDTO dto, Throwable t) {
        log.error("Unit service unavailable for addAvailability (unitId {}): {}", dto.getUnitId(), t.getMessage());
        throw new ServiceUnavailableException("Unit service is currently unavailable");
    }
}