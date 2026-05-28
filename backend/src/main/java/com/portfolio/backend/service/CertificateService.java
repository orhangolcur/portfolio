package com.portfolio.backend.service;

import com.portfolio.backend.dto.certificate.CertificateRequest;
import com.portfolio.backend.dto.certificate.CertificateResponse;
import com.portfolio.backend.entity.Certificate;
import com.portfolio.backend.mapper.CertificateMapper;
import com.portfolio.backend.repository.CertificateRepository;
import com.portfolio.backend.service.rules.CertificateBusinessRules;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class CertificateService {

    private final CertificateRepository certificateRepository;
    private final CertificateBusinessRules certificateBusinessRules;
    private final CertificateMapper certificateMapper;

    public CertificateService(
            CertificateRepository certificateRepository,
            CertificateBusinessRules certificateBusinessRules,
            CertificateMapper certificateMapper) {
        this.certificateRepository = certificateRepository;
        this.certificateBusinessRules = certificateBusinessRules;
        this.certificateMapper = certificateMapper;
    }

    @Transactional(readOnly = true)
    public List<CertificateResponse> getAll() {
        return certificateMapper.toResponseList(certificateBusinessRules.getAllWithTranslations());
    }

    @Transactional(readOnly = true)
    public CertificateResponse getById(UUID id) {
        return certificateMapper.toResponse(certificateBusinessRules.getByIdOrThrow(id));
    }

    @Transactional
    public CertificateResponse create(CertificateRequest request) {
        certificateBusinessRules.checkDuplicateTitleOrThrow(request.getTitleTr());
        Certificate saved = certificateRepository.save(certificateMapper.toEntity(request));
        return certificateMapper.toResponse(saved);
    }

    @Transactional
    public CertificateResponse update(UUID id, CertificateRequest request) {
        Certificate certificate = certificateBusinessRules.getByIdOrThrow(id);
        certificateBusinessRules.checkDuplicateTitleExcludingOrThrow(request.getTitleTr(), id);

        certificate.setIssuer(request.getIssuer());
        certificate.setCredentialUrl(request.getCredentialUrl());
        certificate.setImageUrl(request.getImageUrl());
        certificate.setIssuedDate(request.getIssuedDate());

        certificateMapper.updateTranslation(certificate, "tr", request.getTitleTr());
        certificateMapper.updateTranslation(certificate, "en", request.getTitleEn());

        return certificateMapper.toResponse(certificateRepository.save(certificate));
    }

    @Transactional
    public void delete(UUID id) {
        certificateBusinessRules.checkExistsOrThrow(id);
        certificateRepository.deleteById(id);
    }
}
