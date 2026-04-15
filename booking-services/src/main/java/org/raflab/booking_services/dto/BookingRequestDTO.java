package org.raflab.booking_services.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class BookingRequestDTO {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotNull
    @Schema(
            type = "string",
            format = "date",
            example = "2025-01-30",
            description = "Event date in ISO 8601 date format (yyyy-MM-dd)"
    )
    private Date startDate;

    @NotNull
    @Schema(
            type = "string",
            format = "date",
            example = "2025-01-30",
            description = "Event date in ISO 8601 date format (yyyy-MM-dd)"
    )
    private Date endDate;

    @NotBlank
    @Schema(example = "PENDING")
    private String status;

    private Long unitId;

    private Long policyId;
}
