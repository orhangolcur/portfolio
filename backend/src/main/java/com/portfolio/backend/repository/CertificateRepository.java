package com.portfolio.backend.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.portfolio.backend.entity.Certificate;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, UUID> {

    @Query("SELECT c FROM Certificate c LEFT JOIN FETCH c.translations ORDER BY c.issuedDate DESC")
    List<Certificate> findAllWithTranslations();

    @Query("SELECT c FROM Certificate c LEFT JOIN FETCH c.translations WHERE c.id = :id")
    Optional<Certificate> findByIdWithTranslations(@Param("id") UUID id);

    @Query("SELECT COUNT(c) > 0 FROM Certificate c JOIN c.translations t WHERE t.locale = 'tr' AND t.title = :title")
    boolean existsByTrTitle(@Param("title") String title);

    @Query("SELECT COUNT(c) > 0 FROM Certificate c JOIN c.translations t WHERE t.locale = 'tr' AND t.title = :title AND c.id <> :excludeId")
    boolean existsByTrTitleExcluding(@Param("title") String title, @Param("excludeId") UUID excludeId);
}
