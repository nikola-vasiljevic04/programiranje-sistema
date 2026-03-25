package org.raflab.booking_services.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BookingStatusLogRequestDTO {
    @NotBlank
    private String newStatus;
}
