package org.raflab.finance_services.client.dto;

import lombok.Data;

@Data
public class AddOnItemDTO {
    private Long id;

    private Long bookingId;

    private String serviceName;

    private Double price;
}
