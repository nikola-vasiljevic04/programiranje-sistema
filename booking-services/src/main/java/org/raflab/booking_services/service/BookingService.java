package org.raflab.booking_services.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.raflab.booking_services.client.FinanceClientService;
import org.raflab.booking_services.client.UnitClientService;
import org.raflab.booking_services.client.dto.AvailabilityRequestDTO;
import org.raflab.booking_services.client.dto.UnitDTO;
import org.raflab.booking_services.dto.*;
import org.raflab.booking_services.factory.RefundCalculator;
import org.raflab.booking_services.factory.RefundCalculatorFactory;
import org.raflab.booking_services.mapper.AddOnMapper;
import org.raflab.booking_services.mapper.BookingMapper;
import org.raflab.booking_services.mapper.BookingStatusLogMapper;
import org.raflab.booking_services.model.*;
import org.raflab.booking_services.observer.BookingStatusPublisher;
import org.raflab.booking_services.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final GuestRepository guestRepository;
    private final CancellationPolicyRepository policyRepository;
    private final BookingStatusLogRepository bookingStatusLogRepository;
    private final AddOnItemRepository addOnItemRepository;
    private final BookingMapper bookingMapper;
    private final BookingStatusLogMapper bookingStatusLogMapper;
    private final AddOnMapper addOnMapper;
    private final BookingStatusPublisher statusPublisher;
    private final RefundCalculatorFactory refundCalculatorFactory;
    private final FinanceClientService financeClientService;
    private final UnitClientService unitClientService;

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

    // Create new booking
    @Transactional
    public BookingResponseDTO createBooking(BookingRequestDTO dto) {
        Booking booking = bookingMapper.toEntity(dto);
        String oldStatus = booking.getStatus();
        String newStatus = dto.getStatus();

        Long unitId = dto.getUnitId();
        UnitDTO unit = unitClientService.getUnitById(unitId);

        LocalDate localStartDate = dto.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localEndDate = dto.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        Boolean available = unitClientService.available(unitId,localStartDate, localEndDate);

        if (!available) throw new IllegalArgumentException("Unit is already booked for that date");

        Double totalPrice = unit.getCalculatedPrice();
        booking.setTotalPrice(totalPrice);

        Guest guest = guestRepository.findById(dto.getGuestId())
                .orElseThrow(() -> new EntityNotFoundException("Guest not found"));
        booking.setGuest(guest);

        if (dto.getPolicyId() != null) {
            CancellationPolicy policy = policyRepository.findById(dto.getPolicyId())
                    .orElseThrow(() -> new EntityNotFoundException("Policy not found"));
            booking.setCancellationPolicy(policy);
        }

        Booking savedBooking = bookingRepository.save(booking);
        statusPublisher.notifyStatusChange(savedBooking, oldStatus, newStatus);

        AvailabilityRequestDTO availabilityRequest = new AvailabilityRequestDTO();
        availabilityRequest.setUnitId(unitId);
        availabilityRequest.setStartDate(localStartDate);
        availabilityRequest.setEndDate(localEndDate);
        availabilityRequest.setReason("Reserved on booking " + booking.getId());

        unitClientService.addAvailability(availabilityRequest);

        return bookingMapper.toResponseDTO(savedBooking);
    }

    // Get all booking logs
    public List<BookingStatusLogDTO> getLogsForBooking(Long bookingId) {
        if (!bookingRepository.existsById(bookingId)) {
            throw new EntityNotFoundException("Booking not found");
        }
        return bookingStatusLogRepository.findByBookingIdOrderByChangedAtDesc((bookingId))
                .stream()
                .map(bookingStatusLogMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Update status for booking
    @Transactional
    public BookingResponseDTO updateStatus(Long bookingId, BookingStatusLogRequestDTO request) {
        Booking booking = findBookingById(bookingId);
        String oldStatus = booking.getStatus();
        String newStatus = request.getNewStatus();

        if (oldStatus.equalsIgnoreCase(newStatus)){ // on not modified
            return bookingMapper.toResponseDTO(booking);
        }

        booking.setStatus(newStatus);
        Booking updatedBooking = bookingRepository.save(booking);
        statusPublisher.notifyStatusChange(updatedBooking, oldStatus, newStatus);

        return bookingMapper.toResponseDTO(updatedBooking);
    }

    @Transactional
    public double cancelBooking(Long bookingId) {
        Booking booking = findBookingById(bookingId);

        if ("CANCELLED".equalsIgnoreCase(booking.getStatus()))
            throw new IllegalStateException("Booking is already cancelled");

        RefundCalculator calculator = refundCalculatorFactory.createCalculator(
                booking.getCancellationPolicy()
        );
        double refund = calculator.calculate(booking.getTotalPrice());

        String oldStatus = booking.getStatus();
        booking.setStatus("CANCELLED");
        bookingRepository.save(booking);
        statusPublisher.notifyStatusChange(booking, oldStatus, "CANCELLED");

        try {
            financeClientService.revokeInvoiceForBooking(bookingId);
        } catch (Exception e) {
            if (e.getMessage().contains("already revoked") || e.getMessage().contains("not found")) return refund;
            throw e;
        }

        return refund;
    }

    @Transactional
    public AddOnItemDTO addAddOnToBooking(Long bookingId, AddOnItemDTO dto) {
        Booking booking = findBookingById(bookingId);

        AddOnItem item = addOnMapper.toEntity(dto);
        item.setBooking(booking);

        // update total price
        booking.setTotalPrice(booking.getTotalPrice() + item.getPrice());

        AddOnItem savedItem = addOnItemRepository.save(item);
        bookingRepository.save(booking);

        return addOnMapper.toDTO(savedItem);
    }

    public List<AddOnItemDTO> getAddOnsForBooking(Long bookingId) {
        if (!bookingRepository.existsById(bookingId)) {
            throw new EntityNotFoundException("Booking not found");
        }
        return addOnItemRepository.findByBookingId((bookingId)).stream()
                .map(addOnMapper::toDTO)
                .collect(Collectors.toList());
    }
}