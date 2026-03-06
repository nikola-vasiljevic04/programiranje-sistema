package org.raflab.finance_services.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "tax_rates")
@AllArgsConstructor
@NoArgsConstructor
public class TaxRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false) // not unique, can potentially have "PDV, 20" and "PDV, 15"
    private String taxName;

    @NotNull
    @Min(0) @Max(100)
    @Column(nullable = false)
    private Double percentage;
}
