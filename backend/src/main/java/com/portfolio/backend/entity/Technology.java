package com.portfolio.backend.entity;

import org.hibernate.annotations.UuidGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "technologies")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Technology {

    @Id
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "icon_url")
    private String iconUrl;

    @Column(name = "category")
    private String category;

    @ToString.Exclude // toString metodu kullanılmaz
    @EqualsAndHashCode.Exclude // equals ve hashCode metodu kullanılmaz. sonsuz döngü engellenir
    @ManyToMany(mappedBy = "technologies")
    @Builder.Default
    private List<Project> projects = new ArrayList<>();
}