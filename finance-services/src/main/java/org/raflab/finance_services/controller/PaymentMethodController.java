package org.raflab.finance_services.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.raflab.finance_services.dto.PaymentMethodDTO;
import org.raflab.finance_services.service.PaymentMethodService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment-methods")
@RequiredArgsConstructor
@Tag(name = "Payment Methods", description = "Accepted payment methods management")
public class PaymentMethodController {
    private final PaymentMethodService paymentMethodService;

    @PostMapping
    @Operation(summary = "Create a new payment method", description = "Adds a new payment method to the system. The name must be unique.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Method created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data (e.g. empty name)", content = @Content),
            @ApiResponse(responseCode = "409", description = "Payment method with that name already exists", content = @Content)
    })
    public ResponseEntity<PaymentMethodDTO> create(@RequestBody @Valid PaymentMethodDTO dto) {
        return ResponseEntity.status(201).body(paymentMethodService.createPaymentMethod(dto));
    }
}