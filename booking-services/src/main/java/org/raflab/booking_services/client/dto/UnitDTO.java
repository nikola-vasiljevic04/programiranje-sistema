package org.raflab.booking_services.client.dto;

import lombok.Data;

@Data
public class UnitDTO {
    private Long id;

    private String name;

    private String unitType;

    private Double basePricePerNight;

    private Double calculatedPrice;
}
