package org.raflab.booking_services.client;

import org.raflab.booking_services.client.dto.UnitDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;

@FeignClient(name = "unit-client", configuration = UnitErrorDecoder.class)
public interface UnitClient {
    @GetMapping("/{id}")
    UnitDTO getUnitById(@PathVariable Long id);

    // TODO: Is Unit available (range)
    @GetMapping("/{id}/available")
    boolean available(@PathVariable Long id, Date startDate, Date endDate);
}