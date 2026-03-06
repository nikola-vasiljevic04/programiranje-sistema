package org.raflab.finance_services.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.raflab.finance_services.dto.TaxRateDTO;
import org.raflab.finance_services.service.TaxRateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tax-rates")
@RequiredArgsConstructor
@Tag(name = "Tax Rates", description = "Tax catalog management")
public class TaxRateController {
    private final TaxRateService taxRateService;

    @PostMapping
    @Operation(summary = "Create a new tax rate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tax Rate created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data (e.g. empty name)", content = @Content),
    })
    public ResponseEntity<TaxRateDTO> create(@RequestBody @Valid TaxRateDTO dto) {
        return ResponseEntity.status(201).body(taxRateService.createTaxRate(dto));
    }
}