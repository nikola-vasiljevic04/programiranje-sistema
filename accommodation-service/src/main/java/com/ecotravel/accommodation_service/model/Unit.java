package com.ecotravel.accommodation_service.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Unit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String unitType; // npr. SATOR, KOLIBA
    private Double basePricePerNight;

   // @ManyToOne
    //private Address address;
}
