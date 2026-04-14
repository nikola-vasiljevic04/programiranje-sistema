package org.raflab.authservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.raflab.authservice.model.Role;

@Data
public class RoleReqDTO {
    @NotNull
    private Long userId;

    @NotNull
    private Role role;
}
