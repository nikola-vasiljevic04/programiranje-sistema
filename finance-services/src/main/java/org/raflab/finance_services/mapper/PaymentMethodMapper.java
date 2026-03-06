package org.raflab.finance_services.mapper;

import org.mapstruct.Mapper;
import org.raflab.finance_services.dto.PaymentMethodDTO;
import org.raflab.finance_services.model.PaymentMethod;

@Mapper(componentModel = "spring")
public interface PaymentMethodMapper {
    PaymentMethodDTO toDTO(PaymentMethod entity);
    PaymentMethod toEntity(PaymentMethodDTO dto);
}