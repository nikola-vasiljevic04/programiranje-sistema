package com.ecotravel.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "certifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Certification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;
    private LocalDate expiryDate;

    @Column(name = "issuing_body")
    private String issuingBody;

    @ManyToOne
    @JoinColumn(name = "guide_id")
    private GuideProfile guide;
}