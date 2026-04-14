package com.ecotravel.controller;

import com.ecotravel.dtos.AdventureReviewRequestDTO;
import com.ecotravel.dtos.AdventureReviewResponseDTO;
import com.ecotravel.service.AdventureReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class AdventureReviewController {

    private final AdventureReviewService reviewService;

    @GetMapping
    public ResponseEntity<List<AdventureReviewResponseDTO>> getAll() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdventureReviewResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.getReviewById(id));
    }

    @GetMapping("/adventure/{adventureId}")
    public ResponseEntity<List<AdventureReviewResponseDTO>> getByAdventure(@PathVariable Long adventureId) {
        return ResponseEntity.ok(reviewService.getReviewsByAdventure(adventureId));
    }

    @PostMapping
    public ResponseEntity<AdventureReviewResponseDTO> addReview(@Valid @RequestBody AdventureReviewRequestDTO dto) {
        return new ResponseEntity<>(reviewService.addReview(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdventureReviewResponseDTO> updateReview(
            @PathVariable Long id,
            @Valid @RequestBody AdventureReviewRequestDTO dto) {
        return ResponseEntity.ok(reviewService.updateReview(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}