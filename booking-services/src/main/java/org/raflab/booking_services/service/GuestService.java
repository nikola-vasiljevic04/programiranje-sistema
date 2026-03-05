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

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GuestService {
    private final GuestRepository guestRepository;
    private final GuestMapper guestMapper;

    public Page<GuestDTO> getGuests(Pageable pageable) {
        return guestRepository.findAll(pageable)
                .map(guestMapper::toDTO);
    }

    private Guest findGuestById(Long id) {
        Optional<Guest> guest = guestRepository.findById(id);
        return guest.orElseThrow(() -> new EntityNotFoundException("Guest not found"));
    }

    public GuestDTO getGuestById(Long id) {
        Guest guest = findGuestById(id);
        return guestMapper.toDTO(guest);
    }

    public GuestDTO createGuest(GuestDTO guestDTO) {
        if (guestRepository.existsByEmail(guestDTO.getEmail())) {
            throw new DataIntegrityViolationException("Email already exists");
        }
        Guest guest = guestMapper.toEntity(guestDTO);
        return guestMapper.toDTO(guestRepository.save(guest));
    }

    public GuestDTO updateGuest(Long id, GuestDTO guestDTO) {
        Guest guest = findGuestById(id);
        guestMapper.updateEntityFromDto(guestDTO, guest);

        return guestMapper.toDTO(guestRepository.save(guest));
    }

    public void deleteGuest(Long id) {
        if (!guestRepository.existsById(id)) {
            throw new EntityNotFoundException("Guest not found");
        }
        guestRepository.deleteById(id);
    }
}
