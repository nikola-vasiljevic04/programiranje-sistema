package org.raflab.booking_services.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.raflab.booking_services.dto.AddOnItemDTO;
import org.raflab.booking_services.mapper.AddOnMapper;
import org.raflab.booking_services.model.AddOnItem;
import org.raflab.booking_services.model.Booking;
import org.raflab.booking_services.repository.AddOnItemRepository;
import org.raflab.booking_services.repository.BookingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AddOnService {
    private final AddOnItemRepository addOnItemRepository;
    private final BookingRepository bookingRepository;
    private final AddOnMapper addOnMapper;

    private AddOnItem findAddOnById(Long id) {
        return addOnItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Add-on item not found"));
    }

    public AddOnItemDTO getById(Long id) {
        return addOnMapper.toDTO(findAddOnById(id));
    }

    @Transactional
    public AddOnItemDTO updateAddOn(Long id, AddOnItemDTO dto) {
        AddOnItem item = findAddOnById(id);
        Booking booking = item.getBooking();

        if (booking != null) { // update booking totalPrice
            Double priceDifference = dto.getPrice() - item.getPrice();
            booking.setTotalPrice(booking.getTotalPrice() + priceDifference);
            bookingRepository.save(booking);
        }

        item.setServiceName(dto.getServiceName());
        item.setPrice(dto.getPrice());

        return addOnMapper.toDTO(addOnItemRepository.save(item));
    }

    @Transactional
    public void deleteAddOn(Long id) {
        AddOnItem item = findAddOnById(id);
        Booking booking = item.getBooking();

        if (booking != null) { // remove from booking total price
            booking.setTotalPrice(booking.getTotalPrice() - item.getPrice());
            bookingRepository.save(booking);
        }

        addOnItemRepository.delete(item);
    }
}