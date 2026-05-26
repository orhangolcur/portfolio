package com.portfolio.backend.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.portfolio.backend.entity.Certificate;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, UUID> {
    @Query("SELECT c FROM Certificate c JOIN FETCH c.translations")
    List<Certificate> findAllWithTranslations();
}
