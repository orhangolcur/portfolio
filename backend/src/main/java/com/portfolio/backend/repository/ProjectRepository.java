package com.portfolio.backend.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.portfolio.backend.entity.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {

    // Anasayfa için sadece featured projeler
    @Query("SELECT p FROM Project p JOIN FETCH p.translations ORDER BY p.displayOrder ASC")
    List<Project> findAllWithTranslations();

    @Query("SELECT p FROM Project p JOIN FETCH p.translations WHERE p.featured = true ORDER BY p.displayOrder ASC")
    List<Project> findFeaturedWithTranslations();

    @Query("SELECT p FROM Project p JOIN FETCH p.translations JOIN FETCH p.technologies WHERE p.id = :id")
    Optional<Project> findByIdWithTranslationsAndTechnologies(UUID id);

}
