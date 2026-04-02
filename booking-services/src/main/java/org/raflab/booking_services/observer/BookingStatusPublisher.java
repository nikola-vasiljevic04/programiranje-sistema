package org.raflab.booking_services.observer;

import lombok.RequiredArgsConstructor;
import org.raflab.booking_services.model.Booking;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookingStatusPublisher {
    private final List<BookingStatusObserver> observers;

    public void notifyStatusChange(Booking booking, String oldStatus, String newStatus) {
        observers.forEach(o -> o.onStatusChanged(booking, oldStatus, newStatus));
    }
}
