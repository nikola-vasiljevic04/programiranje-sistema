package org.raflab.booking_services.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.raflab.booking_services.dto.CancellationPolicyDTO;
import org.raflab.booking_services.service.CancellationPolicyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/policies")
@RequiredArgsConstructor
@Tag(name = "Cancellation Policies", description = "Management of refund rules and cancellation terms")
public class CancellationPolicyController {
    private final CancellationPolicyService cancellationPolicyService;

    @GetMapping
    @Operation(summary = "Get all cancellation policies")
    @ApiResponse(responseCode = "200", description = "List of policies")
    public ResponseEntity<List<CancellationPolicyDTO>> getAll() {
        return ResponseEntity.ok(cancellationPolicyService.getAllPolicies());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get policy by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Policy found", content = @Content(schema = @Schema(implementation = CancellationPolicyDTO.class))),
            @ApiResponse(responseCode = "404", description = "Policy not found")
    })
    public ResponseEntity<CancellationPolicyDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(cancellationPolicyService.getPolicyById(id));
    }

    @PostMapping
    @Operation(summary = "Create new cancellation policy")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Policy created"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "409", description = "Policy with that name already exists", content = @Content)
    })
    public ResponseEntity<CancellationPolicyDTO> create(@RequestBody @Valid CancellationPolicyDTO dto) {
        return ResponseEntity.status(201).body(cancellationPolicyService.createPolicy(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update policy")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Policy updated"),
            @ApiResponse(responseCode = "404", description = "Policy not found")
    })
    public ResponseEntity<CancellationPolicyDTO> update(@PathVariable Long id, @RequestBody @Valid CancellationPolicyDTO dto) {
        return ResponseEntity.ok(cancellationPolicyService.updatePolicy(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete policy")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Policy deleted"),
            @ApiResponse(responseCode = "404", description = "Policy not found")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cancellationPolicyService.deletePolicy(id);
        return ResponseEntity.noContent().build();
    }
}