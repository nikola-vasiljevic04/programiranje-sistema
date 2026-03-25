package org.raflab.finance_services.dto;

import lombok.Data;
import org.raflab.finance_services.client.dto.BookingDTO;

@Data
public class InvoiceResponseDTO {
    private Long id;

    private Double subtotal;

    private Double totalAmount;

    private String status;

    private TaxRateDTO taxRate;

    private BookingDTO booking;
}
