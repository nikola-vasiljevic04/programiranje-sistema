package org.raflab.booking_services.observer;

import lombok.RequiredArgsConstructor;
import org.raflab.booking_services.model.Booking;
import org.raflab.booking_services.model.BookingStatusLog;
import org.raflab.booking_services.repository.BookingStatusLogRepository;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class BookingStatusLogObserver implements BookingStatusObserver {
    private final BookingStatusLogRepository statusLogRepository;

    @Override
    public void onStatusChanged(Booking booking, String oldStatus, String newStatus) {
        BookingStatusLog log = new BookingStatusLog();
        log.setBooking(booking);
        log.setOldStatus(oldStatus);
        log.setNewStatus(newStatus);
        log.setChangedAt(new Date());
        statusLogRepository.save(log);
    }
}
