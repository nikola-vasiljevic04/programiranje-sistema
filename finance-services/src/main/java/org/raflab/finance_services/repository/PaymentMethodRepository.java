package org.raflab.finance_services.repository;

import jakarta.validation.constraints.NotBlank;
import org.raflab.finance_services.model.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
    @Query("select count(p.methodName) > 0 from PaymentMethod p where p.methodName = :methodName")
    boolean existsByMethodName(@NotBlank String methodName);
}
