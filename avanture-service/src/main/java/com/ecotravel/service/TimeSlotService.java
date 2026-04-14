package com.ecotravel.service;

import com.ecotravel.dtos.TimeSlotRequestDTO;
import com.ecotravel.dtos.TimeSlotResponseDTO;
import com.ecotravel.mapper.AdventureMapper;
import com.ecotravel.model.Adventure;
import com.ecotravel.model.TimeSlot;
import com.ecotravel.repository.AdventureRepository;
import com.ecotravel.repository.TimeSlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TimeSlotService {

    private final TimeSlotRepository timeSlotRepository;
    private final AdventureRepository adventureRepository;
    private final AdventureMapper adventureMapper;

    // --- TVOJE POSTOJEĆE METODE ---

    public TimeSlotResponseDTO createTimeSlot(TimeSlotRequestDTO dto) {
        Adventure adventure = adventureRepository.findById(dto.getAdventureId())
                .orElseThrow(() -> new RuntimeException("Avantura nije pronađena"));

        TimeSlot timeSlot = adventureMapper.toEntity(dto);
        timeSlot.setAdventure(adventure);

        return adventureMapper.toResponseDTO(timeSlotRepository.save(timeSlot));
    }

    public boolean bookPlace(Long id, int num) {
        TimeSlot slot = timeSlotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Termin nije pronađen"));

        int limit = slot.getMaxParticipants();
        int current = (slot.getCurrentOccupancy() != null) ? slot.getCurrentOccupancy() : 0;

        if (current + num <= limit) {
            slot.setCurrentOccupancy(current + num);
            timeSlotRepository.save(slot);
            return true;
        }
        return false;
    }

    public List<TimeSlotResponseDTO> getSlotsByAdventure(Long adventureId) {
        return timeSlotRepository.findByAdventureId(adventureId).stream()
                .map(adventureMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // --- NOVE CRUD METODE ---

    // Izlistavanje svih termina
    public List<TimeSlotResponseDTO> getAllTimeSlots() {
        return timeSlotRepository.findAll().stream()
                .map(adventureMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Pronalaženje termina po ID-u
    public TimeSlotResponseDTO getTimeSlotById(Long id) {
        TimeSlot slot = timeSlotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Termin sa ID " + id + " nije pronađen"));
        return adventureMapper.toResponseDTO(slot);
    }

    // Ažuriranje (Update)
    public TimeSlotResponseDTO updateTimeSlot(Long id, TimeSlotRequestDTO dto) {
        TimeSlot existingSlot = timeSlotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Termin nije pronađen"));

        Adventure adventure = adventureRepository.findById(dto.getAdventureId())
                .orElseThrow(() -> new RuntimeException("Avantura nije pronađena"));

        existingSlot.setAdventure(adventure);
        existingSlot.setStartTime(dto.getStartTime());
        existingSlot.setEndTime(dto.getEndTime());
        existingSlot.setMaxParticipants(dto.getMaxParticipants());

        return adventureMapper.toResponseDTO(timeSlotRepository.save(existingSlot));
    }

    // Brisanje (Delete)
    public void deleteTimeSlot(Long id) {
        if (!timeSlotRepository.existsById(id)) {
            throw new RuntimeException("Neuspešno brisanje: Termin ne postoji");
        }
        timeSlotRepository.deleteById(id);
    }
}