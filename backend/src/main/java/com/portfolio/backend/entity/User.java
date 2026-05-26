package com.portfolio.backend.entity;

import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.UuidGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data // Lombok annotation.Getter, setter, toString, equals, hashCode otomatik üretilir.
@Builder // User.builder().email("x").build() gibi kullanım sağlar.
@NoArgsConstructor
@AllArgsConstructor // JPA boş constructor ister, Builder ise dolu constructor ister. İkisi birden olmalı.
public class User {
    
    @Id
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt; 

    @PrePersist // Kayıt veritabanına ilk yazılmadan hemen önce çalışır.
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
