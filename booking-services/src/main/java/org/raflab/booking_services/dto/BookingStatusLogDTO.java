package org.raflab.booking_services.dto;

import lombok.Data;

import java.util.Date;

@Data
public class BookingStatusLogDTO {
    private Long id;
    private String oldStatus;
    private String newStatus;
    private Date changedAt;
}
