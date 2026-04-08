package org.raflab.booking_services.client;

import org.raflab.booking_services.client.dto.AvailabilityRequestDTO;
import org.raflab.booking_services.client.dto.UnitDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@FeignClient(name = "unit-client", url = "http://localhost:8081/api", configuration = UnitErrorDecoder.class)
public interface UnitClient {
    @GetMapping("/units/{id}")
    UnitDTO getUnitById(@PathVariable Long id);

    @GetMapping("/units/{id}/check-availability")
    Boolean available(@PathVariable Long id,
                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut);

    @PostMapping("/availability")
    void addAvailability(@RequestBody AvailabilityRequestDTO dto);
}