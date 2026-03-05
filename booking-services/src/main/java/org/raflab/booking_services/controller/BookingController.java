package org.raflab.booking_services.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.raflab.booking_services.dto.BookingRequestDTO;
import org.raflab.booking_services.dto.BookingResponseDTO;
import org.raflab.booking_services.dto.BookingStatusLogDTO;
import org.raflab.booking_services.dto.BookingStatusLogRequestDTO;
import org.raflab.booking_services.service.BookingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
@Tag(name = "Bookings", description = "Endpoints for creating, updating, and retrieving reservations")
public class BookingController {
    private final BookingService bookingService;

    @GetMapping
    @Operation(summary = "Get paginated bookings")
    @ApiResponse(responseCode = "200", description = "Bookings retrieved successfully")
    public ResponseEntity<Page<BookingResponseDTO>> getAll(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(bookingService.getBookings(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get booking by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the booking", content = @Content(schema = @Schema(implementation = BookingResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Booking not found", content = @Content)
    })
    public ResponseEntity<BookingResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Booking created successfully", content = @Content(schema = @Schema(implementation = BookingResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "404", description = "Guest or Policy not found", content = @Content)
    })
    public ResponseEntity<BookingResponseDTO> create(@RequestBody @Valid BookingRequestDTO request) {
        return ResponseEntity.status(201).body(bookingService.createBooking(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update booking details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking updated successfully", content = @Content(schema = @Schema(implementation = BookingResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "404", description = "Booking, Guest or Policy not found", content = @Content)
    })
    public ResponseEntity<BookingResponseDTO> update(@PathVariable Long id, @RequestBody @Valid BookingRequestDTO request) {
        return ResponseEntity.ok(bookingService.updateBooking(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Booking deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Booking not found", content = @Content)
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }

    /* Booking logs */
    @PatchMapping("/{id}/status")
    @Operation(summary = "Update booking status", description = "Specifically updates the booking status and creates a history log.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status updated successfully", content = @Content(schema = @Schema(implementation = BookingResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "404", description = "Booking not found")
    })
    public ResponseEntity<BookingResponseDTO> updateStatus(@PathVariable Long id, @RequestBody @Valid BookingStatusLogRequestDTO request) {
        return ResponseEntity.ok(bookingService.updateStatus(id, request));
    }

    @GetMapping("/{id}/logs")
    @Operation(summary = "Get booking status history", description = "Retrieves a chronological list of all status changes for this booking.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logs retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Booking not found")
    })
    public ResponseEntity<List<BookingStatusLogDTO>> getLogs(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getLogsForBooking(id));
    }
}