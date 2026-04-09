package com.ecotravel.service;

import com.ecotravel.dtos.AdventureReviewRequestDTO;
import com.ecotravel.dtos.AdventureReviewResponseDTO;
import com.ecotravel.mapper.AdventureMapper;
import com.ecotravel.model.Adventure;
import com.ecotravel.model.AdventureReview;
import com.ecotravel.repository.AdventureRepository;
import com.ecotravel.repository.AdventureReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdventureReviewService {

    private final AdventureReviewRepository reviewRepository;
    private final AdventureRepository adventureRepository;
    private final AdventureMapper adventureMapper;

    // 1. Kreiranje (POST)
    public AdventureReviewResponseDTO addReview(AdventureReviewRequestDTO dto) {
        Adventure adventure = adventureRepository.findById(dto.getAdventureId())
                .orElseThrow(() -> new RuntimeException("Avantura nije pronađena"));

        AdventureReview review = adventureMapper.toEntity(dto);
        review.setAdventure(adventure);

        return adventureMapper.toResponseDTO(reviewRepository.save(review));
    }

    // 2. Čitanje svih (GET)
    public List<AdventureReviewResponseDTO> getAllReviews() {
        return reviewRepository.findAll().stream()
                .map(adventureMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // 3. Čitanje po ID-u (GET)
    public AdventureReviewResponseDTO getReviewById(Long id) {
        AdventureReview review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recenzija sa ID " + id + " nije pronađena"));
        return adventureMapper.toResponseDTO(review);
    }

    // 4. Čitanje svih recenzija za jednu avanturu (GET)
    public List<AdventureReviewResponseDTO> getReviewsByAdventure(Long adventureId) {
        return reviewRepository.findByAdventureId(adventureId).stream()
                .map(adventureMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // 5. Ažuriranje (PUT)
    public AdventureReviewResponseDTO updateReview(Long id, AdventureReviewRequestDTO dto) {
        AdventureReview existingReview = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recenzija nije pronađena"));

        // Ažuriramo samo komentar i ocenu
        existingReview.setRating(dto.getRating());
        existingReview.setComment(dto.getComment());

        return adventureMapper.toResponseDTO(reviewRepository.save(existingReview));
    }

    // 6. Brisanje (DELETE)
    public void deleteReview(Long id) {
        if (!reviewRepository.existsById(id)) {
            throw new RuntimeException("Neuspešno brisanje: Recenzija ne postoji");
        }
        reviewRepository.deleteById(id);
    }
}