package org.raflab.booking_services.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "finance-client", url = "http://localhost:8091/api/invoices")
public interface FinanceClient {
    @DeleteMapping("/booking/{id}")
    void revokeInvoiceForBooking(@PathVariable Long id);
}