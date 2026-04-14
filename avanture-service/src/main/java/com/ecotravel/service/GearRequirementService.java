package com.ecotravel.service;

import com.ecotravel.dtos.GearRequirementDTO;
import com.ecotravel.mapper.AdventureMapper;
import com.ecotravel.model.Adventure;
import com.ecotravel.model.GearRequirement;
import com.ecotravel.repository.AdventureRepository;
import com.ecotravel.repository.GearRequirementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GearRequirementService {

    private final GearRequirementRepository gearRepository;
    private final AdventureRepository adventureRepository;
    private final AdventureMapper adventureMapper;

    public GearRequirementDTO addGearToAdventure(GearRequirementDTO dto) {
        Adventure adventure = adventureRepository.findById(dto.getAdventureId())
                .orElseThrow(() -> new RuntimeException("Avantura nije pronađena"));

        GearRequirement gear = adventureMapper.toEntity(dto);
        gear.setAdventure(adventure);

        return adventureMapper.toDTO(gearRepository.save(gear));
    }

    public List<GearRequirementDTO> getAllGear() {
        return gearRepository.findAll().stream()
                .map(adventureMapper::toDTO)
                .collect(Collectors.toList());
    }

    public GearRequirementDTO getGearById(Long id) {
        GearRequirement gear = gearRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Oprema sa ID " + id + " nije pronađena"));
        return adventureMapper.toDTO(gear);
    }

    public List<GearRequirementDTO> getGearByAdventure(Long adventureId) {
        return gearRepository.findByAdventureId(adventureId).stream()
                .map(adventureMapper::toDTO)
                .collect(Collectors.toList());
    }

    public GearRequirementDTO updateGear(Long id, GearRequirementDTO dto) {
        GearRequirement existingGear = gearRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Oprema nije pronađena"));

        // Sada koristimo tačna imena tvojih polja iz DTO/Entiteta!
        existingGear.setItemName(dto.getItemName());
        existingGear.setIsMandatory(dto.getIsMandatory());

        return adventureMapper.toDTO(gearRepository.save(existingGear));
    }

    public void deleteGear(Long id) {
        if (!gearRepository.existsById(id)) {
            throw new RuntimeException("Neuspešno brisanje: Oprema ne postoji");
        }
        gearRepository.deleteById(id);
    }
}