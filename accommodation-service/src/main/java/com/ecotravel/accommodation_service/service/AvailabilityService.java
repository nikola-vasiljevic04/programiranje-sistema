package com.ecotravel.accommodation_service.service;

import com.ecotravel.accommodation_service.dto.AvailabilityDTO;
import com.ecotravel.accommodation_service.dto.AvailabilityRequestDTO;
import com.ecotravel.accommodation_service.model.AvailabilityCalendar;
import com.ecotravel.accommodation_service.model.Unit;
import com.ecotravel.accommodation_service.repository.AvailabilityCalendarRepository;
import com.ecotravel.accommodation_service.repository.UnitRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvailabilityService {

    private final AvailabilityCalendarRepository availabilityRepository;
    private final UnitRepository unitRepository;

    @Transactional
    public void addAvailability(AvailabilityRequestDTO dto) {
        Unit unit = unitRepository.findById(dto.getUnitId())
                .orElseThrow(() -> new EntityNotFoundException("Smeštaj sa ID " + dto.getUnitId() + " nije pronađen"));

        if (dto.getEndDate().isBefore(dto.getStartDate())) {
            throw new IllegalArgumentException("Datum završetka ne može biti pre početka.");
        }

        boolean exists = availabilityRepository.existsOverlap(dto.getUnitId(), dto.getStartDate(), dto.getEndDate());
        if (exists) {
            throw new IllegalStateException("Ovaj termin se preklapa sa postojećom rezervacijom!");
        }
        AvailabilityCalendar availability = new AvailabilityCalendar();
        availability.setStartDate(dto.getStartDate());
        availability.setEndDate(dto.getEndDate());
        availability.setReason(dto.getReason());
        availability.setUnit(unit);

        availabilityRepository.save(availability);
    }

    public List<AvailabilityDTO> getAvailabilityForUnit(Long unitId) {
        // Provera da li unit uopšte postoji pre listanja kalendara
        if (!unitRepository.existsById(unitId)) {
            throw new EntityNotFoundException("Smeštaj nije pronađen.");
        }

        return availabilityRepository.findByUnitId(unitId).stream()
                .map(a -> new AvailabilityDTO(a.getStartDate(), a.getEndDate(), a.getReason()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteAvailability(Long id) {
        if (!availabilityRepository.existsById(id)) {
            throw new EntityNotFoundException("Zapis u kalendaru sa ID " + id + " ne postoji.");
        }
        availabilityRepository.deleteById(id);
    }
}