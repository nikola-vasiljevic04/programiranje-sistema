package org.raflab.finance_services.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "invoices")
@AllArgsConstructor
@NoArgsConstructor
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long bookingId;

    @NotNull
    @Min(0)
    @Column(nullable = false)
    private Double subtotal;

    @NotNull
    @Min(0)
    @Column(nullable = false)
    private Double totalAmount;

    @NotBlank
    @Column(nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "tax_rate_id")
    private TaxRate taxRate;
}
