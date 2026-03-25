package org.raflab.finance_services.facade;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.raflab.finance_services.client.BookingClient;
import org.raflab.finance_services.client.dto.BookingDTO;
import org.raflab.finance_services.dto.InvoiceResponseDTO;
import org.raflab.finance_services.mapper.InvoiceMapper;
import org.raflab.finance_services.model.Invoice;
import org.raflab.finance_services.repository.InvoiceRepository;
import org.raflab.finance_services.repository.TransactionRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InvoiceRevocationFacade {
    private final InvoiceRepository invoiceRepository;
    private final TransactionRepository transactionRepository;
    private final BookingClient bookingClient;
    private final InvoiceMapper invoiceMapper;

    @Transactional
    public InvoiceResponseDTO revokeInvoice(Long invoiceId) {
        if  (invoiceId == null) {
            throw new EntityNotFoundException("Invoice not found");
        }
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new EntityNotFoundException("Invoice not found"));

        validateRevocable(invoice);

        BookingDTO booking = bookingClient.getBookingById(invoice.getBookingId());
        validateBookingRevocable(booking);

        processRefunds(invoiceId);

        invoice.setStatus("REVOKED");
        Invoice saved = invoiceRepository.save(invoice);

        return invoiceMapper.toResponseDTO(saved);
    }

    private void validateRevocable(Invoice invoice) {
        if ("REVOKED".equalsIgnoreCase(invoice.getStatus())) {
            throw new IllegalStateException("Invoice is already revoked");
        }
    }

    private void validateBookingRevocable(BookingDTO booking) {
        if ("COMPLETED".equalsIgnoreCase(booking.getStatus())) {
            throw new IllegalStateException("Cannot revoke invoice for a completed booking");
        }
    }

    private void processRefunds(Long invoiceId) {
        transactionRepository.refundTransactionsByInvoiceId(invoiceId);
    }
}
