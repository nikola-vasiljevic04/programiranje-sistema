package org.raflab.finance_services.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class TransactionRequestDTO {
    @NotNull
    private Long invoiceId;

    @NotNull
    private Long paymentMethodId;

    @NotNull
    @Min(0)
    @Schema(example = "50")
    private Double amount;

    @NotNull
    @Schema(
            type = "string",
            format = "date",
            example = "2025-01-30",
            description = "Event date in ISO 8601 date format (yyyy-MM-dd)"
    )
    private Date transactionDate;
}
