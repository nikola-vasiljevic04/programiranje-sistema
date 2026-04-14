package com.ecotravel.service;

import com.ecotravel.dtos.CertificationRequestDTO;
import com.ecotravel.dtos.CertificationResponseDTO;
import com.ecotravel.mapper.GuideMapper;
import com.ecotravel.model.Certification;
import com.ecotravel.model.GuideProfile;
import com.ecotravel.repository.CertificationRepository;
import com.ecotravel.repository.GuideProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CertificationService {

    private final CertificationRepository certificationRepository;
    private final GuideProfileRepository guideProfileRepository;
    private final GuideMapper guideMapper;

    public CertificationResponseDTO addCertification(CertificationRequestDTO dto) {
        GuideProfile guide = guideProfileRepository.findById(dto.getGuideId())
                .orElseThrow(() -> new RuntimeException("Vodič nije pronađen"));

        Certification certification = guideMapper.toEntity(dto);
        certification.setGuide(guide);

        return guideMapper.toResponseDTO(certificationRepository.save(certification));
    }

    public List<CertificationResponseDTO> getAllCertifications() {
        return certificationRepository.findAll().stream()
                .map(guideMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public CertificationResponseDTO getCertificationById(Long id) {
        Certification certification = certificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sertifikat sa ID " + id + " nije pronađen"));
        return guideMapper.toResponseDTO(certification);
    }

    public List<CertificationResponseDTO> getCertificationsByGuide(Long guideId) {
        return certificationRepository.findByGuideId(guideId).stream()
                .map(guideMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public CertificationResponseDTO updateCertification(Long id, CertificationRequestDTO dto) {
        Certification existingCert = certificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sertifikat nije pronađen"));

        existingCert.setName(dto.getName());
        existingCert.setExpiryDate(dto.getExpiryDate());

        return guideMapper.toResponseDTO(certificationRepository.save(existingCert));
    }

    public void deleteCertification(Long id) {
        if (!certificationRepository.existsById(id)) {
            throw new RuntimeException("Neuspešno brisanje: Sertifikat ne postoji");
        }
        certificationRepository.deleteById(id);
    }
}