package com.ecotravel.accommodation_service.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponseDTO {
    private Long id;
    private String city;
    private String zipCode;
    private String streetAddress;
}
