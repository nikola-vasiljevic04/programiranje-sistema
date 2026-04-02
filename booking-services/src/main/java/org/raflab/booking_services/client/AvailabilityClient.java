package org.raflab.booking_services.client;

import org.raflab.booking_services.client.dto.AvailabilityRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "availability-client", url = "http://localhost:8081/api/availability", configuration = UnitErrorDecoder.class)
public interface AvailabilityClient {
    @PostMapping
    void addAvailability(@RequestBody AvailabilityRequestDTO dto);
}