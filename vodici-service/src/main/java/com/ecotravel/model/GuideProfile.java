package com.ecotravel.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "guide_profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuideProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    @Column(length = 1000)
    private String bio;

    private Double rating;
    private String contactEmail;

    private Double completeness;

    @OneToMany(mappedBy = "guide", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<GuideLanguage> guideLanguages;

    @OneToMany(mappedBy = "guide", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Certification> certifications;
}