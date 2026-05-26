package com.portfolio.backend.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.hibernate.annotations.UuidGenerator;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "certificates")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Certificate {

    @Id
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "issuer")
    private String issuer;

    @Column(name = "credential_url")
    private String credentialUrl;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "issued_date")
    private LocalDate issuedDate;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "certificate", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<CertificateTranslation> translations = new ArrayList<>();
}