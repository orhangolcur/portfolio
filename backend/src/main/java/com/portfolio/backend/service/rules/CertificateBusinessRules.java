package com.portfolio.backend.service.rules;

import com.portfolio.backend.entity.Certificate;
import com.portfolio.backend.exception.BusinessException;
import com.portfolio.backend.exception.ResourceNotFoundException;
import com.portfolio.backend.repository.CertificateRepository;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.UUID;

@Component
public class CertificateBusinessRules {

    private final CertificateRepository certificateRepository;

    public CertificateBusinessRules(CertificateRepository certificateRepository) {
        this.certificateRepository = certificateRepository;
    }

    public Certificate getByIdOrThrow(UUID id) {
        return certificateRepository.findByIdWithTranslations(id)
                .orElseThrow(() -> new ResourceNotFoundException("Certificate not found: " + id));
    }

    public List<Certificate> getAllWithTranslations() {
        return certificateRepository.findAllWithTranslations();
    }

    public void checkExistsOrThrow(UUID id) {
        if (!certificateRepository.existsById(id)) {
            throw new ResourceNotFoundException("Certificate not found: " + id);
        }
    }

    public void checkDuplicateTitleOrThrow(String titleTr) {
        if (certificateRepository.existsByTrTitle(titleTr)) {
            throw new BusinessException("A certificate with this title already exists: " + titleTr);
        }
    }

    public void checkDuplicateTitleExcludingOrThrow(String titleTr, UUID excludeId) {
        if (certificateRepository.existsByTrTitleExcluding(titleTr, excludeId)) {
            throw new BusinessException("A certificate with this title already exists: " + titleTr);
        }
    }
}
