package com.portfolio.backend.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.portfolio.backend.entity.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {

    @Query("SELECT p FROM Project p LEFT JOIN FETCH p.translations LEFT JOIN FETCH p.technologies ORDER BY p.displayOrder ASC")
    List<Project> findAllWithTranslations();

    @Query("SELECT p FROM Project p LEFT JOIN FETCH p.translations LEFT JOIN FETCH p.technologies WHERE p.featured = true ORDER BY p.displayOrder ASC")
    List<Project> findFeaturedWithTranslations();

    @Query("SELECT p FROM Project p LEFT JOIN FETCH p.translations LEFT JOIN FETCH p.technologies WHERE p.id = :id")
    Optional<Project> findByIdWithTranslationsAndTechnologies(UUID id);

    @Query("SELECT COUNT(p) > 0 FROM Project p JOIN p.translations t WHERE t.locale = 'tr' AND t.title = :title")
    boolean existsByTrTitle(@Param("title") String title);

    @Query("SELECT COUNT(p) > 0 FROM Project p JOIN p.translations t WHERE t.locale = 'tr' AND t.title = :title AND p.id <> :excludeId")
    boolean existsByTrTitleExcluding(@Param("title") String title, @Param("excludeId") UUID excludeId);

}
