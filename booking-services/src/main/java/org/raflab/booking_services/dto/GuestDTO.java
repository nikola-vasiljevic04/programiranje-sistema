package org.raflab.booking_services.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GuestDTO {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank
    @Schema(example = "Petar")
    private String firstName;

    @NotBlank
    @Schema(example = "Petrovic")
    private String lastName;

    @NotBlank
    @Schema(example = "+381611...")
    private String phoneNumber;

    @Email
    @Schema(example = "petar@example.com")
    private String email;
}
