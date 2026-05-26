package com.portfolio.backend.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.hibernate.annotations.UuidGenerator;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "about_me")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AboutMe {

    @Id
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Column(name = "github_url")
    private String githubUrl;

    @Column(name = "linkedin_url")
    private String linkedinUrl;

    @Column(name = "cv_url")
    private String cvUrl;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "aboutMe", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<AboutMeTranslation> translations = new ArrayList<>();

    @PreUpdate // Kayıt her güncellendiğinde otomatik çalışır
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}