package org.raflab.booking_services.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.raflab.booking_services.dto.GuestDTO;
import org.raflab.booking_services.mapper.GuestMapper;
import org.raflab.booking_services.model.Guest;
import org.raflab.booking_services.repository.GuestRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuestService {
    private final GuestRepository guestRepository;
    private final GuestMapper guestMapper;

    public Page<GuestDTO> getGuests(Pageable pageable) {
        return guestRepository.findAll(pageable)
                .map(guestMapper::toDTO);
    }

    public GuestDTO createGuest(GuestDTO guestDTO) {
        if (guestRepository.existsByEmail(guestDTO.getEmail())) {
            throw new DataIntegrityViolationException("Email already exists");
        }
        Guest guest = guestMapper.toEntity(guestDTO);
        return guestMapper.toDTO(guestRepository.save(guest));
    }

    public void deleteGuest(Long id) {
        if (!guestRepository.existsById(id)) {
            throw new EntityNotFoundException("Guest not found");
        }
        guestRepository.deleteById(id);
    }
}
