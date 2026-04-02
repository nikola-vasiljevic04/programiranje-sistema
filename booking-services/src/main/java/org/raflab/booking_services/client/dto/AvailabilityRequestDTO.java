package org.raflab.booking_services.client.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AvailabilityRequestDTO {
    private LocalDate startDate;

    private LocalDate endDate;

    private String reason;

    private Long unitId;
}