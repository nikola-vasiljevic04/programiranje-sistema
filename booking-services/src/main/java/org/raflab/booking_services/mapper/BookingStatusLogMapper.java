package org.raflab.booking_services.mapper;

import org.mapstruct.Mapper;
import org.raflab.booking_services.dto.BookingStatusLogDTO;
import org.raflab.booking_services.model.BookingStatusLog;

@Mapper(componentModel = "spring")
public interface BookingStatusLogMapper {
    BookingStatusLogDTO toDTO(BookingStatusLog entity);
}
