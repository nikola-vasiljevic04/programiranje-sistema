package org.raflab.finance_services.dto;

import lombok.Data;

@Data
public class InvoiceResponseDTO {
    private Long id;

    private Double subtotal;

    private Double totalAmount;

    private String status;

    private TaxRateDTO taxRate;
}
