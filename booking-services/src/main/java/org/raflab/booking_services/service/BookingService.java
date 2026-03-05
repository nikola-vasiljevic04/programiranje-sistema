package org.raflab.booking_services.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.raflab.booking_services.dto.BookingRequestDTO;
import org.raflab.booking_services.dto.BookingResponseDTO;
import org.raflab.booking_services.mapper.BookingMapper;
import org.raflab.booking_services.model.Booking;
import org.raflab.booking_services.model.CancellationPolicy;
import org.raflab.booking_services.model.Guest;
import org.raflab.booking_services.repository.BookingRepository;
import org.raflab.booking_services.repository.CancellationPolicyRepository;
import org.raflab.booking_services.repository.GuestRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final GuestRepository guestRepository;
    private final CancellationPolicyRepository policyRepository;
    private final BookingMapper bookingMapper;

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

        System.out.println(dto.getGuestId());

        if (dto.getPolicyId() != null) {
            CancellationPolicy policy = policyRepository.findById(dto.getPolicyId())
                    .orElseThrow(() -> new EntityNotFoundException("Policy not found"));
            booking.setCancellationPolicy(policy);
        }

        return bookingMapper.toResponseDTO(bookingRepository.save(booking));
    }

    @Transactional
    public BookingResponseDTO updateBooking(Long id, BookingRequestDTO dto) {
        Booking booking = findBookingById(id);

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