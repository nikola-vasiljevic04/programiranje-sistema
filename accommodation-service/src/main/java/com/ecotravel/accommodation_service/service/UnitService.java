package com.ecotravel.accommodation_service.service;

import com.ecotravel.accommodation_service.dto.*;
import com.ecotravel.accommodation_service.factory.UnitFactory;
import com.ecotravel.accommodation_service.factory.UnitFactoryRegistry;
import com.ecotravel.accommodation_service.mapper.UnitMapper;
import com.ecotravel.accommodation_service.model.Facility;
import com.ecotravel.accommodation_service.model.PriceTier;
import com.ecotravel.accommodation_service.model.Unit;
import com.ecotravel.accommodation_service.repository.*;
import jakarta.persistence.EntityNotFoundException;
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
    private final UnitFactoryRegistry unitFactoryRegistry;

    public List<UnitResponseDTO> getAllUnits() {
        return unitRepository.findAll().stream()
                .map(unit -> {
                    UnitResponseDTO dto = unitMapper.toResponseDTO(unit);
                    dto.setCalculatedPrice(calculateCurrentPrice(unit));
                    return dto;
                })
                .collect(Collectors.toList());
    }
    public UnitResponseDTO getUnitById(Long id) {
        Unit unit = unitRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Smeštajna jedinica sa ID " + id + " nije pronađena"));
        UnitResponseDTO dto = unitMapper.toResponseDTO(unit);
        dto.setCalculatedPrice(calculateCurrentPrice(unit));
        return dto;
    }
    @Transactional
    public UnitResponseDTO createUnit(UnitRequestDTO dto) {
        UnitFactory factory = unitFactoryRegistry.getFactory(dto.getUnitType());
        Unit unit = factory.createUnit(dto);
        unit.setAddress(unitMapper.toEntity(dto.getAddress()));
        if (dto.getFacilityIds() != null && !dto.getFacilityIds().isEmpty()) {
            List<Facility> facilities = facilityRepository.findAllById(dto.getFacilityIds());
            unit.setFacilities(new HashSet<>(facilities));
        }
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

        if (unit.getFacilities() != null) {
            double facilitiesCost = unit.getFacilities().stream()
                    .mapToDouble(Facility::getExtraCost)
                    .sum();
            total += facilitiesCost;
        }

        LocalDate today = LocalDate.now();
        double multiplier = unit.getPriceTiers().stream()
                .filter(tier -> !today.isBefore(tier.getStartDate()) && !today.isAfter(tier.getEndDate()))
                .map(PriceTier::getMultiplier)
                .findFirst()
                .orElse(1.0);

        return total * multiplier;
    }
    public boolean isUnitAvailable(Long unitId, LocalDate checkIn, LocalDate checkOut) {
        return !availabilityCalendarRepository.existsOverlap(unitId, checkIn, checkOut);
    }
    @Transactional
    public void deleteUnit(Long id) {
        if (!unitRepository.existsById(id)) {
            throw new RuntimeException("Neuspešno brisanje: Smeštaj sa ID " + id + " ne postoji");
        }
        unitRepository.deleteById(id);
    }
    @Transactional
    public UnitResponseDTO updateUnit(Long id, UnitRequestDTO dto) {
        Unit unit = unitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Neuspešno ažuriranje: Smeštaj sa ID " + id + " ne postoji"));

        unit.setName(dto.getName());
        unit.setUnitType(dto.getUnitType());
        unit.setBasePricePerNight(dto.getBasePricePerNight());

        if (unit.getAddress() != null && dto.getAddress() != null) {
            unit.getAddress().setCity(dto.getAddress().getCity());
            unit.getAddress().setZipCode(dto.getAddress().getZipCode());
            unit.getAddress().setStreetAddress(dto.getAddress().getStreetAddress());
        }
        if (dto.getFacilityIds() != null) {
            List<Facility> facilities = facilityRepository.findAllById(dto.getFacilityIds());
            unit.setFacilities(new HashSet<>(facilities));
        }
        Unit updatedUnit = unitRepository.save(unit);
        UnitResponseDTO response = unitMapper.toResponseDTO(updatedUnit);
        response.setCalculatedPrice(calculateCurrentPrice(updatedUnit));
        return response;
    }


    //metode za 3. zad slozeni upiti
    public List<UnitResponseDTO> findByTypeAndCity(String type, String city) {
        return unitRepository.findUnitsByTypeAndCity(type, city).stream()
                .map(unit -> {
                    UnitResponseDTO dto = unitMapper.toResponseDTO(unit);
                    dto.setCalculatedPrice(calculateCurrentPrice(unit));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public Double getAveragePriceByType(String type) {
        Double avgPrice = unitRepository.getAveragePriceForUnitType(type);
        return avgPrice != null ? avgPrice : 0.0;
    }

    public List<UnitResponseDTO> findByFacility(String facilityName) {
        return unitRepository.findUnitsByFacilityName(facilityName).stream()
                .map(unit -> {
                    UnitResponseDTO dto = unitMapper.toResponseDTO(unit);
                    dto.setCalculatedPrice(calculateCurrentPrice(unit));
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
