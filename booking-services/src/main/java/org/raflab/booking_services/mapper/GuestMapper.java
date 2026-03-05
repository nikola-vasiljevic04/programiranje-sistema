package org.raflab.booking_services.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.raflab.booking_services.dto.GuestDTO;
import org.raflab.booking_services.model.Guest;

@Mapper(componentModel = "spring")
public interface GuestMapper {
    GuestDTO toDTO(Guest guest);

    Guest toEntity(GuestDTO guestDTO);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(GuestDTO dto, @MappingTarget Guest entity);
}
