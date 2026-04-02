package org.raflab.finance_services.repository;

import org.raflab.finance_services.model.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("select t from Transaction t where t.invoice.id = :invoiceId")
    List<Transaction> findByInvoiceId(Long invoiceId);

    @Query("select sum(t.amount) from Transaction t where t.invoice.id = :invoiceId")
    Double findTotalPaidByInvoiceId(Long invoiceId);

    @Query("select t from Transaction t where t.amount between :min and :max")
    List<Transaction> findByAmountBetween(Double min, Double max, Pageable pageable);

    @Query("select t from Transaction t where t.invoice.id = :invoiceId and t.amount between :min and :max")
    List<Transaction> findByInvoiceIdAndAmountBetween(Long invoiceId, Double min, Double max, Pageable pageable);

    @Modifying
    @Query("delete Transaction t where t.invoice.id = :invoiceId")
    void refundTransactionsByInvoiceId(Long invoiceId);
}
