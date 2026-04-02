package org.raflab.booking_services.factory;

import org.raflab.booking_services.model.CancellationPolicy;
import org.springframework.stereotype.Component;

@Component
public class RefundCalculatorFactory {
    public RefundCalculator createCalculator(CancellationPolicy policy) {
        if (policy == null) return new NonRefundableCalculator();

        return switch (policy.getPolicyName().toUpperCase()) {
            case "NON_REFUNDABLE" -> new NonRefundableCalculator();
            case "FULL_REFUND"    -> new FullRefundCalculator();
            default               -> new FlexibleRefundCalculator(policy.getRefundPercentage());
        };
    }
}