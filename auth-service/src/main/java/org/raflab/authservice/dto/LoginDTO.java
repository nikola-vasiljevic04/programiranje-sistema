package org.raflab.authservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDTO {
    @Email
    @Schema(example = "petar@example.com")
    private String email;

    @NotBlank
    @Schema(example = "my_strongPassword!", accessMode = Schema.AccessMode.WRITE_ONLY)
    private String password;
}
