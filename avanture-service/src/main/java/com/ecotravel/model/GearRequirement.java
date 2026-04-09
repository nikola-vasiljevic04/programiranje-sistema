package com.ecotravel.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "gear_requirements")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GearRequirement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "adventure_id")
    private Adventure adventure;

    @NotBlank
    private String itemName;
    private Boolean isMandatory;
}