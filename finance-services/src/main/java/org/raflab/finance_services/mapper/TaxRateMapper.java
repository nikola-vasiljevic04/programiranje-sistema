package org.raflab.finance_services.mapper;

import org.mapstruct.Mapper;
import org.raflab.finance_services.dto.TaxRateDTO;
import org.raflab.finance_services.model.TaxRate;

@Mapper(componentModel = "spring")
public interface TaxRateMapper {
    TaxRateDTO toDTO(TaxRate entity);
    TaxRate toEntity(TaxRateDTO dto);
}