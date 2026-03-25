package org.raflab.finance_services.service;

import lombok.RequiredArgsConstructor;
import org.raflab.finance_services.dto.TaxRateDTO;
import org.raflab.finance_services.mapper.TaxRateMapper;
import org.raflab.finance_services.model.TaxRate;
import org.raflab.finance_services.repository.TaxRateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TaxRateService {
    private final TaxRateRepository taxRateRepository;
    private final TaxRateMapper taxRateMapper;

    @Transactional
    public TaxRateDTO createTaxRate(TaxRateDTO dto) {
        TaxRate taxRate = taxRateMapper.toEntity(dto);
        return taxRateMapper.toDTO(taxRateRepository.save(taxRate));
    }
}