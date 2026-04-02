package org.raflab.booking_services.observer;

import org.raflab.booking_services.model.Booking;

public interface BookingStatusObserver {
    void onStatusChanged(Booking booking, String oldStatus, String newStatus);
}
