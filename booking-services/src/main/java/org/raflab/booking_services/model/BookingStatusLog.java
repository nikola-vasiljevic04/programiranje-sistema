package org.raflab.booking_services.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@Table(name = "booking_status_logs")
@AllArgsConstructor @NoArgsConstructor
public class BookingStatusLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String oldStatus;
    
    @NotBlank
    @Column(nullable = false)
    private String newStatus;

    @NotNull
    @Column(nullable = false)
    private Date changedAt;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;
}
