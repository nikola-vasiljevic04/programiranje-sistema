package org.raflab.booking_services.factory;

public class NonRefundableCalculator implements RefundCalculator {
    @Override
    public double calculate(double totalPrice) {
        return 0.0;
    }
}