package com.portfolio.backend.repository;

import com.portfolio.backend.entity.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EducationRepository extends JpaRepository<Education, UUID> {

    @Query("SELECT e FROM Education e LEFT JOIN FETCH e.translations ORDER BY e.startDate DESC")
    List<Education> findAllWithTranslations();

    @Query("SELECT e FROM Education e LEFT JOIN FETCH e.translations WHERE e.id = :id")
    Optional<Education> findByIdWithTranslations(@Param("id") UUID id);
}