package com.ecotravel.accommodation_service.dto;

import lombok.Data;

import java.util.List;

@Data
public class UnitResponseDTO {
    private Long id;
    private String name;
    private String unitType;
    private Double basePricePerNight;
    private AddressResponseDTO address;
    private Double calculatedPrice;
    private List<FacilityDTO> facilities;
    private List<PriceTierDTO> priceTiers;
}
