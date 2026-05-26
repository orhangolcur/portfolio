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
    name = "work_experience_translations",
    uniqueConstraints = @UniqueConstraint(columnNames = {"work_experience_id", "locale"})
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkExperienceTranslation {

    @Id
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_experience_id", nullable = false)
    private WorkExperience workExperience;

    @Column(name = "locale", nullable = false)
    private String locale;

    @Column(name = "position")
    private String position;

    @Column(name = "description", columnDefinition = "TEXT") // uzun metin icin
    private String description;
}