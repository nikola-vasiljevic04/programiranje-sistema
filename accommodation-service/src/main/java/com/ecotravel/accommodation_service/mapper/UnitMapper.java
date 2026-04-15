package com.ecotravel.accommodation_service.mapper;

import com.ecotravel.accommodation_service.dto.*;
import com.ecotravel.accommodation_service.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UnitMapper {

    @Mapping(target = "facilities", ignore = true)
    Unit toEntity(UnitRequestDTO dto);

    UnitResponseDTO toResponseDTO(Unit unit);
    AvailabilityDTO toAvailabilityDTO(AvailabilityCalendar calendar);
    Address toEntity(AddressRequestDTO dto);
    AddressResponseDTO toResponseDTO(Address address);

    FacilityDTO toDTO(Facility facility);
    PriceTierDTO toDTO(PriceTier priceTier);
}