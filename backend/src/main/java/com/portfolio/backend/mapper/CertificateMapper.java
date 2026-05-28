package com.portfolio.backend.mapper;

import com.portfolio.backend.dto.certificate.CertificateRequest;
import com.portfolio.backend.dto.certificate.CertificateResponse;
import com.portfolio.backend.entity.Certificate;
import com.portfolio.backend.entity.CertificateTranslation;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class CertificateMapper {

    public CertificateResponse toResponse(Certificate certificate) {
        String titleTr = getTitleByLocale(certificate, "tr");
        String titleEn = getTitleByLocale(certificate, "en");

        return CertificateResponse.builder()
                .id(certificate.getId())
                .titleTr(titleTr)
                .titleEn(titleEn)
                .issuer(certificate.getIssuer())
                .credentialUrl(certificate.getCredentialUrl())
                .imageUrl(certificate.getImageUrl())
                .issuedDate(certificate.getIssuedDate())
                .build();
    }

    public List<CertificateResponse> toResponseList(List<Certificate> certificates) {
        return certificates.stream()
                .map(this::toResponse)
                .toList();
    }

    public Certificate toEntity(CertificateRequest request) {
        Certificate certificate = Certificate.builder()
                .issuer(request.getIssuer())
                .credentialUrl(request.getCredentialUrl())
                .imageUrl(request.getImageUrl())
                .issuedDate(request.getIssuedDate())
                .build();

        CertificateTranslation trTranslation = CertificateTranslation.builder()
                .certificate(certificate)
                .locale("tr")
                .title(request.getTitleTr())
                .build();

        CertificateTranslation enTranslation = CertificateTranslation.builder()
                .certificate(certificate)
                .locale("en")
                .title(request.getTitleEn())
                .build();

        certificate.getTranslations().add(trTranslation);
        certificate.getTranslations().add(enTranslation);

        return certificate;
    }

    public void updateTranslation(Certificate certificate, String locale, String title) {
        certificate.getTranslations().stream()
                .filter(t -> t.getLocale().equals(locale))
                .findFirst()
                .ifPresentOrElse(
                        t -> t.setTitle(title),
                        () -> certificate.getTranslations().add(
                                CertificateTranslation.builder()
                                        .certificate(certificate)
                                        .locale(locale)
                                        .title(title)
                                        .build()
                        )
                );
    }

    private String getTitleByLocale(Certificate certificate, String locale) {
        return certificate.getTranslations().stream()
                .filter(t -> t.getLocale().equals(locale))
                .findFirst()
                .map(CertificateTranslation::getTitle)
                .orElse("");
    }
}
