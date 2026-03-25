package org.raflab.finance_services.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.raflab.finance_services.dto.InvoiceRequestDTO;
import org.raflab.finance_services.dto.InvoiceResponseDTO;
import org.raflab.finance_services.dto.TransactionRequestDTO;
import org.raflab.finance_services.dto.TransactionResponseDTO;
import org.raflab.finance_services.service.InvoiceService;
import org.raflab.finance_services.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
@Tag(name = "Invoices", description = "Invoice management and tax calculations")
public class InvoiceController {
    private final InvoiceService invoiceService;
    private final TransactionService transactionService;

    @GetMapping
    @Operation(summary = "Get all invoices")
    public ResponseEntity<List<InvoiceResponseDTO>> getAll() {
        return ResponseEntity.ok(invoiceService.getAllInvoices());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get invoice by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Invoice created"),
            @ApiResponse(responseCode = "404", description = "Invoice not found", content = @Content),
            @ApiResponse(responseCode = "404", description = "Booking not found", content = @Content)
    })
    public ResponseEntity<InvoiceResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(invoiceService.getInvoiceById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new invoice", description = "Automatically calculates total amount based on tax rate.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Invoice created"),
            @ApiResponse(responseCode = "400", description = "Invalid input data (e.g. empty name)", content = @Content),
            @ApiResponse(responseCode = "404", description = "Tax Rate not found", content = @Content),
            @ApiResponse(responseCode = "404", description = "Booking not found", content = @Content)
    })
    public ResponseEntity<InvoiceResponseDTO> create(@RequestBody @Valid InvoiceRequestDTO dto) {
        return ResponseEntity.status(201).body(invoiceService.createInvoice(dto));
    }

    @PatchMapping("/{id}/subtotal")
    @Operation(summary = "Update subtotal", description = "Updates subtotal and recalculates total amount.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Invoice subtotal updated"),
            @ApiResponse(responseCode = "404", description = "Invoice not found", content = @Content)
    })
    public ResponseEntity<InvoiceResponseDTO> updateSubtotal(@PathVariable Long id, @RequestParam Double subtotal) {
        return ResponseEntity.ok(invoiceService.updateSubtotal(id, subtotal));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Revokes invoice")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Invoice deleted"),
            @ApiResponse(responseCode = "404", description = "Invoice not found")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        invoiceService.revokeInvoice(id);
        return ResponseEntity.noContent().build();
    }

    /* Transactions */
    @GetMapping("/{id}/transactions")
    @Operation(summary = "Get all transactions for a specific invoice")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved transactions"),
            @ApiResponse(responseCode = "404", description = "Invoice not found", content = @Content)
    })
    public ResponseEntity<List<TransactionResponseDTO>> getTransactions(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.getTransactionsByInvoiceId(id));
    }

    @PostMapping("/{id}/transactions")
    @Operation(
            summary = "Add a transaction to an invoice",
            description = "Creates a transaction for the given invoice. If the transaction amount >= invoice total, the invoice status is updated to PAID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transaction created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input (e.g. negative amount, missing date)", content = @Content),
            @ApiResponse(responseCode = "404", description = "Invoice not found or Payment Method not found", content = @Content)
    })
    public ResponseEntity<TransactionResponseDTO> addTransaction(
            @PathVariable Long id,
            @RequestBody @Valid TransactionRequestDTO dto) {
        return ResponseEntity.status(201).body(transactionService.createTransactionForInvoice(id, dto));
    }
}