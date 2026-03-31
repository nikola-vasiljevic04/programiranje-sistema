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
        // Dodali smo @Valid da bi radile one provere @NotBlank koje smo stavili u DTO
        return ResponseEntity.ok(addressService.createAddress(dto));
    }
}
