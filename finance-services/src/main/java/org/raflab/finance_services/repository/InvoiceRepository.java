package org.raflab.finance_services.repository;

import org.raflab.finance_services.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    @Query("select i from Invoice i where i.bookingId = :id and i.status != 'REVOKED'")
    Optional<Invoice> findValidInvoiceByBookingId(Long id);

    Optional<Invoice> getInvoiceById(Long id);

    Optional<Invoice> findInvoiceByIdAndStatusNot(Long id, String status);
}
