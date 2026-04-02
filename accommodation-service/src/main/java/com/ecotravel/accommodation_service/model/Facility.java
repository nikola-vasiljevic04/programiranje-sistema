package com.ecotravel.accommodation_service.model;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name = "facilities")
public class Facility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "extra_cost")
    private Double extraCost;
}
