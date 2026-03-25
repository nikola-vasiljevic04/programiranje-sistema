package org.raflab.booking_services.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddOnItemDTO {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long bookingId;

    @NotBlank
    @Schema(example = "Parking")
    private String serviceName;

    @NotNull
    @Schema(example = "250", minimum = "0")
    @Min(0)
    private Double price;
}
