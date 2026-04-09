package com.ecotravel.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "guide_languages")
@Data
public class GuideLanguage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "guide_id")
    @JsonIgnore  // <-- DODAJ OVO OBAVEZNO
    private GuideProfile guide;

    @ManyToOne
    @JoinColumn(name = "language_id")
    private Language language;
}