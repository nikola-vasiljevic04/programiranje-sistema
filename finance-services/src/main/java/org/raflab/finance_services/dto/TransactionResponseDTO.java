package org.raflab.finance_services.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TransactionResponseDTO {
    private Long id;

    private Double amount;

    private Date transactionDate;

    private InvoiceResponseDTO invoice;

    private PaymentMethodDTO paymentMethod;
}
