package org.raflab.booking_services.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "finance-client", url = "${finance.service.url}")
public interface FinanceClient {
    @DeleteMapping("/invoices/booking/{id}")
    void revokeInvoiceForBooking(@PathVariable Long id);
}