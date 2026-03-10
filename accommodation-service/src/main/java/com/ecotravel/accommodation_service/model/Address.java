package com.ecotravel.accommodation_service.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@Table(name = "addresses")
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "street_address")
    private String streetAddress;
}
