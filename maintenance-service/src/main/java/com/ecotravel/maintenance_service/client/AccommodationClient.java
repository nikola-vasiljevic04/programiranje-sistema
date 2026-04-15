package com.ecotravel.maintenance_service.client;

import com.ecotravel.maintenance_service.client.dto.UnitResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@FeignClient(name = "accommodation-service", url = "http://localhost:8081",configuration = UnitErrorDecoder.class)
public interface AccommodationClient {

    @GetMapping("/api/units/{id}")
    UnitResponseDTO getUnitById(@PathVariable("id") Long id);

    @GetMapping("/api/units")
    List<UnitResponseDTO> getAllUnits();

    @GetMapping("/api/units/{id}/check-availability")
    Boolean checkAvailability(
            @PathVariable("id") Long id,
            @RequestParam("checkIn") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
            @RequestParam("checkOut") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut
    );
}