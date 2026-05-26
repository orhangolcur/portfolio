package com.portfolio.backend.repository;

import com.portfolio.backend.entity.AboutMe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AboutMeRepository extends JpaRepository<AboutMe, UUID> {
    // N+1 problemini çözmek için JPQL yazdık. JOIN FETCH ile tek sorguda hem entity'yi hem ilişkili verilerini çekiyoruz.
    @Query("SELECT a FROM AboutMe a LEFT JOIN FETCH a.translations")
    Optional<AboutMe> findWithTranslations();
}
