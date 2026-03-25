package org.raflab.booking_services.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "bookings")
@AllArgsConstructor @NoArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long unitId; // from external service

    @NotNull
    @Column(nullable = false)
    private Date startDate;

    @NotNull
    @Column(nullable = false)
    private Date endDate;

    @NotNull
    @Column(nullable = false)
    @Min(0)
    private Double totalPrice;

    @NotBlank
    @Column(nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "guest_id")
    private Guest guest;

    @ManyToOne
    @JoinColumn(name = "policy_id")
    private CancellationPolicy cancellationPolicy;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<AddOnItem> addOns;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<BookingStatusLog> statusLog;
}
