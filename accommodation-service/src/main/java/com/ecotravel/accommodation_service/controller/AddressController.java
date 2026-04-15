package com.ecotravel.accommodation_service.controller;
import com.ecotravel.accommodation_service.dto.AddressRequestDTO;
import com.ecotravel.accommodation_service.dto.AddressResponseDTO;
import com.ecotravel.accommodation_service.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;
    @GetMapping
    public List<AddressResponseDTO> getAll() {
        return addressService.getAllAddresses();
    }

    @PostMapping
    public ResponseEntity<AddressResponseDTO> create(@Valid @RequestBody AddressRequestDTO dto) {
        return ResponseEntity.ok(addressService.createAddress(dto));
    }
}
