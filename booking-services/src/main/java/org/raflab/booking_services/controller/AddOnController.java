package org.raflab.booking_services.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.raflab.booking_services.dto.AddOnItemDTO;
import org.raflab.booking_services.service.AddOnService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/addons")
@RequiredArgsConstructor
@Tag(name = "Add-ons", description = "Management of additional services like parking, breakfast, etc.")
public class AddOnController {
    private final AddOnService addOnService;

    @GetMapping("/{id}")
    @Operation(summary = "Get Add-on item by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item found", content = @Content(schema = @Schema(implementation = AddOnItemDTO.class))),
            @ApiResponse(responseCode = "404", description = "Item not found")
    })
    public ResponseEntity<AddOnItemDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(addOnService.getById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Add-on item", description = "Updates item details and recalculates booking total price")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item updated successfully", content = @Content(schema = @Schema(implementation = AddOnItemDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Item not found")
    })
    public ResponseEntity<AddOnItemDTO> update(@PathVariable Long id, @RequestBody @Valid AddOnItemDTO dto) {
        return ResponseEntity.ok(addOnService.updateAddOn(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Add-on item", description = "Removes item and decreases booking total price")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Item not found")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        addOnService.deleteAddOn(id);
        return ResponseEntity.noContent().build();
    }
}