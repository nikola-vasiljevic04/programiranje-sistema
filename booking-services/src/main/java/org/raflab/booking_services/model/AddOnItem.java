package org.raflab.booking_services.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "add_on_items")
@AllArgsConstructor @NoArgsConstructor
public class AddOnItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String serviceName;

    @NotNull
    @Column(nullable = false)
    @Min(0)
    private Double price;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;
}
