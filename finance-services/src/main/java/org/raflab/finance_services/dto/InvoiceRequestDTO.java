package org.raflab.finance_services.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InvoiceRequestDTO {
    @NotNull
    private Long taxRateId;

    @NotNull
    @Min(0)
    @Schema(example = "30")
    private Double subtotal;

    @NotNull
    @Min(0)
    @Schema(example = "42")
    private Double totalAmount;

    @NotBlank
    @Schema(example = "UNPAID")
    private String status;
}
