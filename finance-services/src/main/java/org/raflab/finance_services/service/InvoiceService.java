package org.raflab.finance_services.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.raflab.finance_services.client.BookingClient;
import org.raflab.finance_services.client.dto.BookingDTO;
import org.raflab.finance_services.dto.InvoiceRequestDTO;
import org.raflab.finance_services.dto.InvoiceResponseDTO;
import org.raflab.finance_services.facade.InvoiceRevocationFacade;
import org.raflab.finance_services.mapper.InvoiceMapper;
import org.raflab.finance_services.model.Invoice;
import org.raflab.finance_services.model.TaxRate;
import org.raflab.finance_services.repository.InvoiceRepository;
import org.raflab.finance_services.repository.TaxRateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final TaxRateRepository taxRateRepository;
    private final InvoiceMapper invoiceMapper;
    private final BookingClient bookingClient;
    private final InvoiceRevocationFacade revocationFacade;

    @Transactional(readOnly = true)
    public List<InvoiceResponseDTO> getAllInvoices() {
        return invoiceRepository.findAll().stream()
                .map(invoiceMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public InvoiceResponseDTO getInvoiceById(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Invoice not found with id: " + id));
        BookingDTO bookingDTO = bookingClient.getBookingById(invoice.getBookingId());

        InvoiceResponseDTO dto = invoiceMapper.toResponseDTO(invoice);
        dto.setBooking(bookingDTO);

        return dto;
    }

    @Transactional
    public InvoiceResponseDTO createInvoice(InvoiceRequestDTO dto) {
        TaxRate taxRate = taxRateRepository.findById(dto.getTaxRateId()).orElseThrow(() -> new EntityNotFoundException("Tax Rate not found"));
        BookingDTO booking = bookingClient.getBookingById(dto.getBookingId());

        Invoice validInvoice = invoiceRepository.findValidInvoiceByBookingId(booking.getId()).orElse(null);
        if (validInvoice != null) {
            throw new IllegalArgumentException("Invoice already exists with id: " + validInvoice.getId());
        }

        if (!dto.getSubtotal().equals(booking.getTotalPrice())) {
            throw new IllegalArgumentException("Subtotal should be: " + booking.getTotalPrice());
        }

        Invoice invoice = invoiceMapper.toEntity(dto);
        invoice.setTaxRate(taxRate);

        double total = invoice.getSubtotal() * (1 + taxRate.getPercentage() / 100);
        invoice.setTotalAmount(total);

        return invoiceMapper.toResponseDTO(invoiceRepository.save(invoice));
    }

    @Transactional
    public InvoiceResponseDTO updateSubtotal(Long id, Double newSubtotal) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Invoice not found"));

        BookingDTO booking = bookingClient.getBookingById(invoice.getBookingId());
        if (!newSubtotal.equals(booking.getTotalPrice())) {
            throw new IllegalArgumentException("Subtotal should be: " + booking.getTotalPrice());
        }

        invoice.setSubtotal(newSubtotal);
        double total = newSubtotal * (1 + invoice.getTaxRate().getPercentage() / 100);
        invoice.setTotalAmount(total);

        return invoiceMapper.toResponseDTO(invoiceRepository.save(invoice));
    }

    @Transactional
    public InvoiceResponseDTO revokeInvoice(Long id) {
        return revocationFacade.revokeInvoice(id);
    }

    @Transactional
    public InvoiceResponseDTO revokeInvoiceForBookingId(Long bookingId) {
        Invoice invoice = invoiceRepository.findValidInvoiceByBookingId(bookingId).orElse(null);
        if (invoice == null) {
            throw new IllegalArgumentException("Invoice not found.");
        }
        return revocationFacade.revokeInvoice(invoice.getId());
    }
}