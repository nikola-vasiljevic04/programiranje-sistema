package org.raflab.authservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.raflab.authservice.model.Role;

@Data
public class UserDTO {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank
    @Schema(example = "Petar")
    private String firstName;

    @NotBlank
    @Schema(example = "Petrovic")
    private String lastName;

    @Email
    @Schema(example = "petar@example.com")
    private String email;

    @NotBlank
    @Schema(example = "my_strongPassword!", accessMode = Schema.AccessMode.WRITE_ONLY)
    private String password;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Role role;
}
