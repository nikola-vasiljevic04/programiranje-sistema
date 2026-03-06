package org.raflab.finance_services.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.raflab.finance_services.dto.InvoiceRequestDTO;
import org.raflab.finance_services.dto.InvoiceResponseDTO;
import org.raflab.finance_services.model.Invoice;

@Mapper(componentModel = "spring", uses = {TaxRateMapper.class})
public interface InvoiceMapper {

    InvoiceResponseDTO toResponseDTO(Invoice entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "taxRate", ignore = true)
    Invoice toEntity(InvoiceRequestDTO dto);
}