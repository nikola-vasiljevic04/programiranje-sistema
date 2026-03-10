package com.ecotravel.accommodation_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "units")
@Getter @Setter
@NoArgsConstructor
public class Unit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "unit_type")
    private String unitType;

    @Column(name = "base_price_per_night")
    private Double basePricePerNight;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToMany
    @JoinTable(
            name = "unit_facilities",
            joinColumns = @JoinColumn(name = "unit_id"),
            inverseJoinColumns = @JoinColumn(name = "facility_id")
    )
    private Set<Facility> facilities = new HashSet<>();

    @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL)
    private List<PriceTier> priceTiers = new ArrayList<>();
}
