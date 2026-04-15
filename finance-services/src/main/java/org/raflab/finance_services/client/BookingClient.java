package org.raflab.finance_services.client;

import org.raflab.finance_services.client.dto.BookingDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "booking-client", url="${booking.service.url}", configuration = BookingErrorDecoder.class)
public interface BookingClient {
    @GetMapping("/bookings/{id}")
    BookingDTO getBookingById(@PathVariable Long id);
}
