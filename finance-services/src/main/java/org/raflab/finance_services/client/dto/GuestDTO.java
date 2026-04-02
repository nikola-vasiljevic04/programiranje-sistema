package org.raflab.finance_services.client.dto;

import lombok.Data;

@Data
public class GuestDTO {
    private Long id;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String email;
}
