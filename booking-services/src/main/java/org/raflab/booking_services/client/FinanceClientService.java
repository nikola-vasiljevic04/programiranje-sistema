package org.raflab.booking_services.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.raflab.booking_services.exception.ServiceUnavailableException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FinanceClientService {
    private final FinanceClient financeClient;

    @CircuitBreaker(name = "finance-service", fallbackMethod = "revokeInvoiceForBookingFallback")
    @Retry(name = "finance-service")
    public void revokeInvoiceForBooking(Long id) {
        financeClient.revokeInvoiceForBooking(id);
    }

    public void revokeInvoiceForBookingFallback(Long id, EntityNotFoundException e) {
        throw e;
    }

    public void revokeInvoiceForBookingFallback(Long id, Throwable t) {
        log.error("Finance service unavailable for id {}: {}", id, t.getMessage());
        throw new ServiceUnavailableException("Finance service is currently unavailable");
    }
}
