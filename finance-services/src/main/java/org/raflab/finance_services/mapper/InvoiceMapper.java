package org.raflab.finance_services.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.raflab.finance_services.dto.InvoiceRequestDTO;
import org.raflab.finance_services.dto.InvoiceResponseDTO;
import org.raflab.finance_services.model.Invoice;

@Mapper(componentModel = "spring", uses = {TaxRateMapper.class})
public interface InvoiceMapper {

    @Mapping(target="booking", ignore = true)
    InvoiceResponseDTO toResponseDTO(Invoice entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "taxRate", ignore = true)
    Invoice toEntity(InvoiceRequestDTO dto);
}