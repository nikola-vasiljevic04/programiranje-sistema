package org.raflab.booking_services.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "guests")
@AllArgsConstructor @NoArgsConstructor
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String firstName;
    @NotBlank
    @Column(nullable = false)
    private String lastName;

    @NotNull
    @NotBlank
    private String phoneNumber;

    @Column(unique = true, nullable = false)
    private String email;

    @OneToMany(mappedBy = "guest", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings;
}
