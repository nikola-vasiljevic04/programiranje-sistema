package org.raflab.booking_services.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.raflab.booking_services.dto.BookingRequestDTO;
import org.raflab.booking_services.dto.BookingResponseDTO;
import org.raflab.booking_services.model.Booking;

@Mapper(componentModel = "spring", uses = {GuestMapper.class, CancellationPolicyMapper.class, AddOnMapper.class})
public interface BookingMapper {
    @Mapping(source = "cancellationPolicy", target = "policy")
    BookingResponseDTO toResponseDTO(Booking booking);

    @Mapping(target = "guest", ignore = true)
    @Mapping(target = "cancellationPolicy", ignore = true)
    @Mapping(target = "addOns", ignore = true)
    Booking toEntity(BookingRequestDTO bookingDTO);
}
