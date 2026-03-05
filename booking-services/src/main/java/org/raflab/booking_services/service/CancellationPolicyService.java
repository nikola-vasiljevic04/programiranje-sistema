package org.raflab.booking_services.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.raflab.booking_services.dto.CancellationPolicyDTO;
import org.raflab.booking_services.mapper.CancellationPolicyMapper;
import org.raflab.booking_services.model.CancellationPolicy;
import org.raflab.booking_services.repository.CancellationPolicyRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CancellationPolicyService {
    private final CancellationPolicyRepository cancellationPolicyRepository;
    private final CancellationPolicyMapper cancellationPolicyMapper;

    public List<CancellationPolicyDTO> getAllPolicies() {
        return cancellationPolicyRepository.findAll().stream()
                .map(cancellationPolicyMapper::toDTO)
                .collect(Collectors.toList());
    }

    private CancellationPolicy findPolicyById(Long id) {
        return cancellationPolicyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cancellation policy not found"));
    }

    public CancellationPolicyDTO getPolicyById(Long id) {
        return cancellationPolicyMapper.toDTO(findPolicyById(id));
    }

    public CancellationPolicyDTO createPolicy(CancellationPolicyDTO dto) {
        if (cancellationPolicyRepository.existsByPolicyName(dto.getPolicyName())) {
            throw new DataIntegrityViolationException("Cancellation policy already exists");
        }
        CancellationPolicy policy = cancellationPolicyMapper.toEntity(dto);
        return cancellationPolicyMapper.toDTO(cancellationPolicyRepository.save(policy));
    }

    public CancellationPolicyDTO updatePolicy(Long id, CancellationPolicyDTO dto) {
        CancellationPolicy policy = findPolicyById(id);
        cancellationPolicyMapper.updateEntityFromDto(dto, policy);
        return cancellationPolicyMapper.toDTO(cancellationPolicyRepository.save(policy));
    }

    public void deletePolicy(Long id) {
        if (!cancellationPolicyRepository.existsById(id)) {
            throw new EntityNotFoundException("Cancellation policy not found");
        }
        cancellationPolicyRepository.deleteById(id);
    }
}