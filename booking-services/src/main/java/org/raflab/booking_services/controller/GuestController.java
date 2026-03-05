package org.raflab.booking_services.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.raflab.booking_services.dto.GuestDTO;
import org.raflab.booking_services.service.GuestService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/guests")
@RequiredArgsConstructor
@Tag(name = "Guests", description = "Operations related to guest profile management")
public class GuestController {
    private final GuestService guestService;

    @GetMapping
    @Operation(summary = "Get Guests paginated")
    @ApiResponse(responseCode = "200", description = "Guests")
    public ResponseEntity<Page<GuestDTO>> get(Pageable pageable) {
        return ResponseEntity.ok(guestService.getGuests(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Guest by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Guest found", content = @Content(schema = @Schema(implementation = GuestDTO.class))),
            @ApiResponse(responseCode = "404", description = "Guest not found", content = @Content)
    })
    public ResponseEntity<GuestDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(guestService.getGuestById(id));
    }

    @PostMapping
    @Operation(summary = "Create Guest")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Guest created successfully", content = @Content(schema = @Schema(implementation = GuestDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "409", description = "Guest with that email already exists", content = @Content)
    })
    public ResponseEntity<GuestDTO> create(@RequestBody @Valid final GuestDTO guestRequest) {
        return ResponseEntity.status(201).body(guestService.createGuest(guestRequest));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Guest", description = "Updates an existing guest's details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Guest updated successfully"),
            @ApiResponse(responseCode = "404", description = "Guest not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<GuestDTO> update(@PathVariable Long id, @RequestBody @Valid GuestDTO guestRequest) {
        return ResponseEntity.ok(guestService.updateGuest(id, guestRequest));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Guest", description = "Removes a guest from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Guest deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Guest not found")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        guestService.deleteGuest(id);
        return ResponseEntity.noContent().build();
    }
}
