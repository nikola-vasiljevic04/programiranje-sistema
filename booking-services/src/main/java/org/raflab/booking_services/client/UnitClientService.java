package org.raflab.booking_services.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.raflab.booking_services.client.dto.AvailabilityRequestDTO;
import org.raflab.booking_services.client.dto.UnitDTO;
import org.raflab.booking_services.exception.ServiceUnavailableException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class UnitClientService {
    private final UnitClient unitClient;

    @CircuitBreaker(name = "unit-service", fallbackMethod = "getUnitByIdFallback")
    @Retry(name = "unit-service")
    public UnitDTO getUnitById(Long id) {
        return unitClient.getUnitById(id);
    }

    public UnitDTO getUnitByIdFallback(Long id, EntityNotFoundException e) {
        throw e;
    }

    public UnitDTO getUnitByIdFallback(Long id, Throwable t) {
        log.error("Unit service unavailable for id {}: {}", id, t.getMessage());
        throw new ServiceUnavailableException("Unit service is currently unavailable");
    }

    @CircuitBreaker(name = "unit-service", fallbackMethod = "availableFallback")
    @Retry(name = "unit-service")
    public Boolean available(Long id, LocalDate checkIn, LocalDate checkOut) {
        return unitClient.available(id, checkIn, checkOut);
    }

    public Boolean availableFallback(Long id, LocalDate checkIn, LocalDate checkOut, EntityNotFoundException e) {
        throw e;
    }

    public Boolean availableFallback(Long id, LocalDate checkIn, LocalDate checkOut, Throwable t) {
        log.error("Unit service unavailable for availability check (id {}, {} - {}): {}", id, checkIn, checkOut, t.getMessage());
        throw new ServiceUnavailableException("Unit service is currently unavailable");
    }

    @CircuitBreaker(name = "unit-service", fallbackMethod = "addAvailabilityFallback")
    @Retry(name = "unit-service")
    public void addAvailability(AvailabilityRequestDTO dto) {
        unitClient.addAvailability(dto);
    }

    public void addAvailabilityFallback(AvailabilityRequestDTO dto, EntityNotFoundException e) {
        throw e;
    }

    public void addAvailabilityFallback(AvailabilityRequestDTO dto, Throwable t) {
        log.error("Unit service unavailable for addAvailability (unitId {}): {}", dto.getUnitId(), t.getMessage());
        throw new ServiceUnavailableException("Unit service is currently unavailable");
    }
}