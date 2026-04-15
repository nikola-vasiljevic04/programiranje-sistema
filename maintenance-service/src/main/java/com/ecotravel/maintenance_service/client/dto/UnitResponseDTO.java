package com.ecotravel.maintenance_service.client.dto;

import lombok.Data;

@Data
public class UnitResponseDTO {
    private Long id;
    private String name;
    private String unitType;
    private Double basePricePerNight;
}
