package com.ecotravel.accommodation_service.mapper;

import com.ecotravel.accommodation_service.dto.*;
import com.ecotravel.accommodation_service.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UnitMapper {

    // Konverzija za POST/PUT
    @Mapping(target = "facilities", ignore = true)
    Unit toEntity(UnitRequestDTO dto);

    // Konverzija za GET (prikaz)
    UnitResponseDTO toResponseDTO(Unit unit);
    AvailabilityDTO toAvailabilityDTO(AvailabilityCalendar calendar);
    // Mapiranje adresa
    Address toEntity(AddressRequestDTO dto);
    AddressResponseDTO toResponseDTO(Address address);

    // Mapiranje za PriceTier i Facility (oni ostaju jednostavni)
    FacilityDTO toDTO(Facility facility);
    PriceTierDTO toDTO(PriceTier priceTier);
}