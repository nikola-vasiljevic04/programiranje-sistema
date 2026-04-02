package org.raflab.finance_services.client.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BookingDTO {
    private Long id;

    private Date startDate;

    private Date endDate;

    private Double totalPrice;

    private String status;

    private Long unitId;

    private GuestDTO guest;
    private CancellationPolicyDTO policy;
    private List<AddOnItemDTO> addOns;
}
