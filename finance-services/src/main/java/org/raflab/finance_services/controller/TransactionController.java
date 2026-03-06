package org.raflab.finance_services.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.raflab.finance_services.dto.TransactionResponseDTO;
import org.raflab.finance_services.service.TransactionService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
@Tag(name = "Transactions", description = "Transactions managment")
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping("/filter")
    @Operation(summary = "Filter all transactions by amount",
            description = "Returns all transactions in the system within the specified price range.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Invalid range parameters")
    })
    public ResponseEntity<List<TransactionResponseDTO>> filter(
            @RequestParam(required = false) Long invoiceId,
            @RequestParam(required = false) Double min,
            @RequestParam(required = false) Double max,
            Pageable pageable) {
        return ResponseEntity.ok(transactionService.filterTransactions(invoiceId, min, max, pageable));
    }
}
