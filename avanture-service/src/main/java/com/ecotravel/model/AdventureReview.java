package com.ecotravel.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "adventure_reviews")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdventureReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "adventure_id")
    private Adventure adventure;

    @Min(value = 1, message = "Ocena mora biti najmanje 1")
    @Max(value = 5, message = "Ocena ne može biti veća od 5")
    private Integer rating;

    @Column(columnDefinition = "TEXT")
    private String comment;

    // Hibernate će sam upisati trenutno vreme pri čuvanju u bazu!
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}