package org.raflab.booking_services.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.raflab.booking_services.dto.CancellationPolicyDTO;
import org.raflab.booking_services.model.CancellationPolicy;

@Mapper(componentModel = "spring")
public interface CancellationPolicyMapper {
    CancellationPolicyDTO toDTO(CancellationPolicy cancellationPolicy);

    CancellationPolicy toEntity(CancellationPolicyDTO cancellationPolicyDTO);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(CancellationPolicyDTO dto, @MappingTarget CancellationPolicy entity);
}
