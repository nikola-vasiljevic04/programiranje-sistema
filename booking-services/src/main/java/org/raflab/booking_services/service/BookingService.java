package org.raflab.booking_services.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.raflab.booking_services.dto.BookingRequestDTO;
import org.raflab.booking_services.dto.BookingResponseDTO;
import org.raflab.booking_services.dto.BookingStatusLogDTO;
import org.raflab.booking_services.dto.BookingStatusLogRequestDTO;
import org.raflab.booking_services.mapper.BookingMapper;
import org.raflab.booking_services.mapper.BookingStatusLogMapper;
import org.raflab.booking_services.model.Booking;
import org.raflab.booking_services.model.BookingStatusLog;
import org.raflab.booking_services.model.CancellationPolicy;
import org.raflab.booking_services.model.Guest;
import org.raflab.booking_services.repository.BookingRepository;
import org.raflab.booking_services.repository.BookingStatusLogRepository;
import org.raflab.booking_services.repository.CancellationPolicyRepository;
import org.raflab.booking_services.repository.GuestRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final GuestRepository guestRepository;
    private final CancellationPolicyRepository policyRepository;
    private final BookingStatusLogRepository bookingStatusLogRepository;
    private final BookingMapper bookingMapper;
    private final BookingStatusLogMapper bookingStatusLogMapper;

    public Page<BookingResponseDTO> getBookings(Pageable pageable) {
        return bookingRepository.findAll(pageable)
                .map(bookingMapper::toResponseDTO);
    }

    private Booking findBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found"));
    }

    public BookingResponseDTO getById(Long id) {
        return bookingMapper.toResponseDTO(findBookingById(id));
    }

    @Transactional
    public BookingResponseDTO createBooking(BookingRequestDTO dto) {
        Booking booking = bookingMapper.toEntity(dto);

        Guest guest = guestRepository.findById(dto.getGuestId())
                .orElseThrow(() -> new EntityNotFoundException("Guest not found"));
        booking.setGuest(guest);

        if (dto.getPolicyId() != null) {
            CancellationPolicy policy = policyRepository.findById(dto.getPolicyId())
                    .orElseThrow(() -> new EntityNotFoundException("Policy not found"));
            booking.setCancellationPolicy(policy);
        }

        // initial log
        this.updateStatus(booking, booking.getStatus(), false);

        return bookingMapper.toResponseDTO(bookingRepository.save(booking));
    }

    public List<BookingStatusLogDTO> getLogsForBooking(Long bookingId) {
        if (!bookingRepository.existsById(bookingId)) {
            throw new EntityNotFoundException("Booking not found");
        }
        return bookingStatusLogRepository.findByBookingIdOrderByChangedAtDesc((bookingId))
                .stream()
                .map(bookingStatusLogMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public BookingResponseDTO updateStatus(Long bookingId, BookingStatusLogRequestDTO request) {
        Booking booking = findBookingById(bookingId);
        String newStatus = request.getNewStatus();

        if (booking.getStatus().equalsIgnoreCase(newStatus)){ // on not modified
            return bookingMapper.toResponseDTO(booking);
        }

        Booking updated = this.updateStatus(booking, newStatus, true);
        return bookingMapper.toResponseDTO(updated);
    }

    private Booking updateStatus(Booking booking, String newStatus, boolean updateBooking) {
        String oldStatus = booking.getStatus();

        if(updateBooking){
            booking.setStatus(newStatus);
            bookingRepository.save(booking);
        }

        BookingStatusLog log = new BookingStatusLog();
        log.setBooking(booking);
        log.setOldStatus(oldStatus);
        log.setNewStatus(newStatus);
        log.setChangedAt(new Date());
        bookingStatusLogRepository.save(log);

        return booking;
    }

    @Transactional
    public BookingResponseDTO updateBooking(Long id, BookingRequestDTO dto) {
        Booking booking = findBookingById(id);

        if (!booking.getStatus().equals(dto.getStatus())) {
            this.updateStatus(booking, dto.getStatus(), false);
        }
        bookingMapper.updateEntityFromDto(dto, booking);

        if (!booking.getGuest().getId().equals(dto.getGuestId())) {
            Guest guest = guestRepository.findById(dto.getGuestId())
                    .orElseThrow(() -> new EntityNotFoundException("Guest not found"));
            booking.setGuest(guest);
        }

        if (booking.getCancellationPolicy() != null && !booking.getCancellationPolicy().getId().equals(dto.getPolicyId())) {
            CancellationPolicy policy = policyRepository.findById(dto.getPolicyId())
                    .orElseThrow(() -> new EntityNotFoundException("Policy not found"));
            booking.setCancellationPolicy(policy);
        }

        return bookingMapper.toResponseDTO(bookingRepository.save(booking));
    }

    public void deleteBooking(Long id) {
        if (!bookingRepository.existsById(id)) {
            throw new EntityNotFoundException("Booking not found");
        }
        bookingRepository.deleteById(id);
    }
}