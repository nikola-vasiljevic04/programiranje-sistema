package org.raflab.finance_services.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.raflab.finance_services.dto.TransactionRequestDTO;
import org.raflab.finance_services.dto.TransactionResponseDTO;
import org.raflab.finance_services.model.Transaction;

@Mapper(componentModel = "spring", uses = {InvoiceMapper.class, PaymentMethodMapper.class})
public interface TransactionMapper {

    TransactionResponseDTO toResponseDTO(Transaction entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "invoice", ignore = true)
    @Mapping(target = "paymentMethod", ignore = true)
    Transaction toEntity(TransactionRequestDTO dto);
}