package org.raflab.gatewayservice.dto;

import lombok.Data;

import java.util.Set;

@Data
public class UserDTO {
    private Long id;

    private String firstName;
    private String lastName;

    private String email;

    private Set<Role> roles;
}
