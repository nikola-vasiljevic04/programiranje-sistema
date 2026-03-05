package org.raflab.booking_services.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CancellationPolicyDTO {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank
    @Schema(example = "50% Refund")
    String policyName;

    @NotNull
    @Min(0) @Max(100)
    @Schema(example = "50", minimum = "0", maximum = "100")
    Integer refundPercentage;
}
