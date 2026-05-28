package com.portfolio.backend.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.portfolio.backend.entity.WorkExperience;

@Repository
public interface WorkExperienceRepository extends JpaRepository<WorkExperience, UUID> {

    @Query("SELECT w FROM WorkExperience w LEFT JOIN FETCH w.translations ORDER BY w.startDate DESC")
    List<WorkExperience> findAllWithTranslations();

    @Query("SELECT w FROM WorkExperience w LEFT JOIN FETCH w.translations WHERE w.id = :id")
    Optional<WorkExperience> findByIdWithTranslations(@Param("id") UUID id);
}
