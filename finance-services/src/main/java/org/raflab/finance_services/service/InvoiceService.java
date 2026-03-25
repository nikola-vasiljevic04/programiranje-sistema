package org.raflab.finance_services.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.raflab.finance_services.dto.InvoiceRequestDTO;
import org.raflab.finance_services.dto.InvoiceResponseDTO;
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
        return invoiceMapper.toResponseDTO(invoice);
    }

    @Transactional
    public InvoiceResponseDTO createInvoice(InvoiceRequestDTO dto) {
        TaxRate taxRate = taxRateRepository.findById(dto.getTaxRateId())
                .orElseThrow(() -> new EntityNotFoundException("Tax Rate not found"));

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

        invoice.setSubtotal(newSubtotal);
        double total = newSubtotal * (1 + invoice.getTaxRate().getPercentage() / 100);
        invoice.setTotalAmount(total);

        return invoiceMapper.toResponseDTO(invoiceRepository.save(invoice));
    }

    @Transactional
    public void deleteInvoice(Long id) {
        if (!invoiceRepository.existsById(id)) {
            throw new EntityNotFoundException("Invoice not found");
        }
        invoiceRepository.deleteById(id);
    }
}