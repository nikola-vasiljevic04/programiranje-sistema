package org.raflab.authservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.raflab.authservice.model.Role;

import java.util.Set;

@Data
public class RoleReqDTO {
    @NotNull
    private Long userId;

    @NotNull
    private Set<Role> roles;
}
