package org.raflab.finance_services.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.raflab.finance_services.client.dto.BookingDTO;
import org.raflab.finance_services.exception.ServiceUnavailableException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingClientService {
    private final BookingClient bookingClient;

    @CircuitBreaker(name = "booking-service", fallbackMethod = "getBookingFallback")
    @Retry(name = "booking-service")
    public BookingDTO getBookingById(Long id) {
        return bookingClient.getBookingById(id);
    }

    public BookingDTO getBookingFallback(Long id, EntityNotFoundException e) {
        throw e;
    }

    public BookingDTO getBookingFallback(Long id, Throwable t) {
        log.error("Booking service unavailable for id {}: {}", id, t.getMessage());
        throw new ServiceUnavailableException("Booking service is currently unavailable");
    }
}