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

    // GET /api/reviews - Lista svih recenzija u sistemu
    @GetMapping
    public ResponseEntity<List<AdventureReviewResponseDTO>> getAll() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    // GET /api/reviews/{id} - Detalji jedne recenzije
    @GetMapping("/{id}")
    public ResponseEntity<AdventureReviewResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.getReviewById(id));
    }

    // GET /api/reviews/adventure/{adventureId} - Sve recenzije za jednu specifičnu avanturu
    @GetMapping("/adventure/{adventureId}")
    public ResponseEntity<List<AdventureReviewResponseDTO>> getByAdventure(@PathVariable Long adventureId) {
        return ResponseEntity.ok(reviewService.getReviewsByAdventure(adventureId));
    }

    // POST /api/reviews - Dodavanje nove recenzije
    @PostMapping
    public ResponseEntity<AdventureReviewResponseDTO> addReview(@Valid @RequestBody AdventureReviewRequestDTO dto) {
        return new ResponseEntity<>(reviewService.addReview(dto), HttpStatus.CREATED);
    }

    // PUT /api/reviews/{id} - Izmena postojeće recenzije
    @PutMapping("/{id}")
    public ResponseEntity<AdventureReviewResponseDTO> updateReview(
            @PathVariable Long id,
            @Valid @RequestBody AdventureReviewRequestDTO dto) {
        return ResponseEntity.ok(reviewService.updateReview(id, dto));
    }

    // DELETE /api/reviews/{id} - Brisanje recenzije
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}