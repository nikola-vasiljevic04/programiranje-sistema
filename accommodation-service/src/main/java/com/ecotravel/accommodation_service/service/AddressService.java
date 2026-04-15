package com.ecotravel.accommodation_service.service;

import com.ecotravel.accommodation_service.dto.AddressRequestDTO;
import com.ecotravel.accommodation_service.dto.AddressResponseDTO;
import com.ecotravel.accommodation_service.mapper.UnitMapper;
import com.ecotravel.accommodation_service.model.Address;
import com.ecotravel.accommodation_service.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final UnitMapper mapper;

    public List<AddressResponseDTO> getAllAddresses() {
        return addressRepository.findAll()
                .stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public AddressResponseDTO createAddress(AddressRequestDTO dto) {
        Address address = mapper.toEntity(dto);
        Address saved = addressRepository.save(address);
        return mapper.toResponseDTO(saved);
    }
}
