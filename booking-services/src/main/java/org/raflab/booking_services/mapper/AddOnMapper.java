package org.raflab.booking_services.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.raflab.booking_services.dto.AddOnItemDTO;
import org.raflab.booking_services.model.AddOnItem;

@Mapper(componentModel = "spring")
public interface AddOnMapper {
    @Mapping(source = "booking.id", target = "bookingId")
    AddOnItemDTO toDTO(AddOnItem entity);

    @Mapping(target = "booking", ignore = true)
    AddOnItem toEntity(AddOnItemDTO dto);
}
