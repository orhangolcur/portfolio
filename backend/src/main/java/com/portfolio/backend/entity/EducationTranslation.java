package com.portfolio.backend.entity;

import java.util.UUID;
import org.hibernate.annotations.UuidGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(
    name = "education_translations",
    uniqueConstraints = @UniqueConstraint(columnNames = {"education_id", "locale"})
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EducationTranslation {

    @Id
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "education_id", nullable = false)
    private Education education;

    @Column(name = "locale", nullable = false)
    private String locale;

    @Column(name = "institution")
    private String institution;

    @Column(name = "field_of_study")
    private String fieldOfStudy;

    @Column(name = "degree")
    private String degree;
}