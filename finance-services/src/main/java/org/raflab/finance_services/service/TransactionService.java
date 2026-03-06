package org.raflab.finance_services.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.raflab.finance_services.dto.TransactionRequestDTO;
import org.raflab.finance_services.dto.TransactionResponseDTO;
import org.raflab.finance_services.mapper.TransactionMapper;
import org.raflab.finance_services.model.Invoice;
import org.raflab.finance_services.model.PaymentMethod;
import org.raflab.finance_services.model.Transaction;
import org.raflab.finance_services.repository.InvoiceRepository;
import org.raflab.finance_services.repository.PaymentMethodRepository;
import org.raflab.finance_services.repository.TransactionRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final InvoiceRepository invoiceRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final TransactionMapper transactionMapper;

    @Transactional
    public TransactionResponseDTO createTransactionForInvoice(Long invoiceId, TransactionRequestDTO dto) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new EntityNotFoundException("Invoice not found"));

        PaymentMethod method = paymentMethodRepository.findById(dto.getPaymentMethodId())
                .orElseThrow(() -> new EntityNotFoundException("Payment Method not found"));

        Transaction transaction = transactionMapper.toEntity(dto);
        transaction.setInvoice(invoice);
        transaction.setPaymentMethod(method);

        if (transaction.getAmount() >= invoice.getTotalAmount()) {
            invoice.setStatus("PAID");
            invoiceRepository.save(invoice);
        }

        return transactionMapper.toResponseDTO(transactionRepository.save(transaction));
    }

    @Transactional(readOnly=true)
    public List<TransactionResponseDTO> getTransactionsByInvoiceId(Long invoiceId) {
        if(!invoiceRepository.existsById(invoiceId)) {
            throw new EntityNotFoundException("Invoice not found");
        }

        return transactionRepository.findByInvoiceId(invoiceId).stream()
                .map(transactionMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TransactionResponseDTO> filterTransactions(Long invoiceId, Double min, Double max, Pageable pageable) {
        Double minVal = (min != null) ? min : 0.0;
        Double maxVal = (max != null) ? max : Double.MAX_VALUE;

        List<Transaction> transactions = invoiceId != null ?
                transactionRepository.findByInvoiceIdAndAmountBetween(invoiceId, minVal, maxVal, pageable)
                : transactionRepository.findByAmountBetween(minVal, maxVal, pageable);

        return transactions.stream().map(transactionMapper::toResponseDTO).collect(Collectors.toList());
    }
}