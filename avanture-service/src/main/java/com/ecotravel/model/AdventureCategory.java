package com.ecotravel.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "adventure_categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdventureCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Ime kategorije je obavezno")
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;
}