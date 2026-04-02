package org.raflab.booking_services.factory;

public class FlexibleRefundCalculator implements RefundCalculator {
    private final double refundPercentage;

    public FlexibleRefundCalculator(double refundPercentage) {
        this.refundPercentage = refundPercentage;
    }

    @Override
    public double calculate(double totalPrice) {
        return totalPrice * (refundPercentage / 100.0);
    }
}