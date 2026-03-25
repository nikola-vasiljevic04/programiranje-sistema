package org.raflab.finance_services.client.dto;

import lombok.Data;

@Data
public class CancellationPolicyDTO {
    private Long id;

    String policyName;

    Integer refundPercentage;
}
