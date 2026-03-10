package com.ecotravel.accommodation_service.service;

import com.ecotravel.accommodation_service.dto.*;
import com.ecotravel.accommodation_service.mapper.UnitMapper;
import com.ecotravel.accommodation_service.model.Facility;
import com.ecotravel.accommodation_service.model.PriceTier;
import com.ecotravel.accommodation_service.model.Unit;
import com.ecotravel.accommodation_service.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UnitService {

    private final UnitRepository unitRepository;
    private final UnitMapper unitMapper;
    private final AddressRepository addressRepository;
    private final FacilityRepository facilityRepository;
    private final AvailabilityCalendarRepository availabilityCalendarRepository;

    // 1. Koristimo UnitResponseDTO za prikaz liste
    public List<UnitResponseDTO> getAllUnits() {
        return unitRepository.findAll().stream()
                .map(unit -> {
                    UnitResponseDTO dto = unitMapper.toResponseDTO(unit);
                    dto.setCalculatedPrice(calculateCurrentPrice(unit));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    // 2. Pronalaženje jedne jedinice
    public UnitResponseDTO getUnitById(Long id) {
        Unit unit = unitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Smeštajna jedinica sa ID " + id + " nije pronađena"));
        UnitResponseDTO dto = unitMapper.toResponseDTO(unit);
        dto.setCalculatedPrice(calculateCurrentPrice(unit));
        return dto;
    }

    // 3. Čuvanje nove jedinice sa RequestDTO
    @Transactional
    public UnitResponseDTO createUnit(UnitRequestDTO dto) {
        // Prebacujemo RequestDTO u Entity (MapStruct će mapirati ime, tip, cenu i adresu)
        Unit unit = unitMapper.toEntity(dto);

        // Moramo ručno povezati objekte na osnovu ID-jeva iz liste facilityIds
        if (dto.getFacilityIds() != null && !dto.getFacilityIds().isEmpty()) {
            List<Facility> facilities = facilityRepository.findAllById(dto.getFacilityIds());
            unit.setFacilities(new HashSet<>(facilities));
        }

        // Adresu čuvamo pre Unit-a ako je kaskadno podešavanje (CascadeType) nije automatsko
        if (unit.getAddress() != null) {
            addressRepository.save(unit.getAddress());
        }

        Unit savedUnit = unitRepository.save(unit);
        UnitResponseDTO response = unitMapper.toResponseDTO(savedUnit);
        response.setCalculatedPrice(calculateCurrentPrice(savedUnit));
        return response;
    }
    private Double calculateCurrentPrice(Unit unit) {
        double total = unit.getBasePricePerNight();

        // dodavanje troskova sadrzaja
        if (unit.getFacilities() != null) {
            double facilitiesCost = unit.getFacilities().stream()
                    .mapToDouble(Facility::getExtraCost)
                    .sum();
            total += facilitiesCost;
        }

        // da li postoji aktuelni PriceTier (sezonska cena)
        LocalDate today = LocalDate.now();
        double multiplier = unit.getPriceTiers().stream()
                .filter(tier -> !today.isBefore(tier.getStartDate()) && !today.isAfter(tier.getEndDate()))
                .map(PriceTier::getMultiplier)
                .findFirst()
                .orElse(1.0); // Ako nema sezone, množilac je 1.0 (nema promene)

        return total * multiplier;
    }
    public boolean isUnitAvailable(Long unitId, LocalDate checkIn, LocalDate checkOut) {
        // Koristimo instancu (malim slovom), ne klasu!
        return !availabilityCalendarRepository.existsOverlap(unitId, checkIn, checkOut);
    }
}
