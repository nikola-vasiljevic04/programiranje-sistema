package org.raflab.booking_services.repository;

import org.raflab.booking_services.model.CancellationPolicy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CancellationPolicyRepository extends JpaRepository<CancellationPolicy, Long> {
    boolean existsByPolicyName(String policyName);
}
