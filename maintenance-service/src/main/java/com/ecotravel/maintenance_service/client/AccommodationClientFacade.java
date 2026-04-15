package com.ecotravel.maintenance_service.client;

import com.ecotravel.maintenance_service.client.dto.UnitResponseDTO;
import com.ecotravel.maintenance_service.exception.ServiceUnavailableException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccommodationClientFacade {
    private final AccommodationClient accommodationClient;

    @CircuitBreaker(name = "accommodation-service", fallbackMethod = "getUnitFallback")
    @Retry(name = "accommodation-service")
    public UnitResponseDTO getUnit(Long id) {
        return accommodationClient.getUnitById(id);
    }

    public UnitResponseDTO getUnitFallback(Long id, EntityNotFoundException e) {
        throw e;
    }

    public UnitResponseDTO getUnitFallback(Long id, Throwable t) {
        log.error("Smeštaj servis nedostupan za ID {}: {}", id, t.getMessage());
        throw new ServiceUnavailableException("Smeštajni servis trenutno nije dostupan");
    }

    @CircuitBreaker(name = "accommodation-service", fallbackMethod = "checkAvailabilityFallback")
    @Retry(name = "accommodation-service")
    public Boolean checkAvailability(Long id, LocalDate checkIn, LocalDate checkOut) {
        return accommodationClient.checkAvailability(id, checkIn, checkOut);
    }

    public Boolean checkAvailabilityFallback(Long id, LocalDate checkIn, LocalDate checkOut, EntityNotFoundException e) {
        throw e;
    }

    public Boolean checkAvailabilityFallback(Long id, LocalDate checkIn, LocalDate checkOut, Throwable t) {
        log.error("Smeštaj servis nedostupan za proveru dostupnosti: {}", t.getMessage());
        throw new ServiceUnavailableException("Smeštajni servis trenutno nije dostupan");
    }
}