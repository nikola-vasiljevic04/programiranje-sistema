package org.raflab.booking_services.factory;

public class FullRefundCalculator implements RefundCalculator {
    @Override
    public double calculate(double totalPrice) {
        return totalPrice;
    }
}