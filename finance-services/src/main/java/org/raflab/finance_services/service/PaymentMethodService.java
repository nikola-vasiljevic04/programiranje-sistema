package org.raflab.finance_services.service;

import lombok.RequiredArgsConstructor;
import org.raflab.finance_services.dto.PaymentMethodDTO;
import org.raflab.finance_services.mapper.PaymentMethodMapper;
import org.raflab.finance_services.model.PaymentMethod;
import org.raflab.finance_services.repository.PaymentMethodRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentMethodService {
    private final PaymentMethodRepository paymentMethodRepository;
    private final PaymentMethodMapper paymentMethodMapper;

    @Transactional
    public PaymentMethodDTO createPaymentMethod(PaymentMethodDTO dto) {
        if (paymentMethodRepository.existsByMethodName(dto.getMethodName())) {
            throw new DataIntegrityViolationException("Payment method with that name already exists");
        }
        PaymentMethod method = paymentMethodMapper.toEntity(dto);
        return paymentMethodMapper.toDTO(paymentMethodRepository.save(method));
    }
}