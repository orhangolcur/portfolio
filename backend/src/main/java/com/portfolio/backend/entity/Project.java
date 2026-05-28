package com.portfolio.backend.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.hibernate.annotations.UuidGenerator;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "projects")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    @Id
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "github_url")
    private String githubUrl;

    @Column(name = "live_url")
    private String liveUrl;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "featured")
    private boolean featured;

    @Column(name = "display_order")
    private int displayOrder;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @ToString.Exclude // toString metodu kullanılmaz
    @EqualsAndHashCode.Exclude // equals ve hashCode metodu kullanılmaz. sonsuz döngü engellenir
    // Bir projenin birden fazla çevirisi olabilir (tr, en)
    @OneToMany(
        mappedBy = "project",
        cascade = CascadeType.ALL, // Project'e yapılan işlemi çevirilere de yansıt
        orphanRemoval = true // listeden çıkarılan çeviriyi DB'den de sil. yani sahipsiz çeviri olmaz
    )
    @Builder.Default // NullPointerException hatası almamak icin kullanılır. @Builder kullanırken list field'larına mutlaka ekle.Artık builder ile oluşturulan nesnede de liste boş ArrayList olarak başlar, null gelmez
    private List<ProjectTranslation> translations = new ArrayList<>();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    // Bir proje birden fazla teknoloji kullanabilir
    @ManyToMany
    @JoinTable(
        name = "project_technologies",
        joinColumns = @JoinColumn(name = "project_id"),
        inverseJoinColumns = @JoinColumn(name = "technology_id")
    )
    @Builder.Default
    private Set<Technology> technologies = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}