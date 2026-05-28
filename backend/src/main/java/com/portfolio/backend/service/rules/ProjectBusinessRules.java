package com.portfolio.backend.service.rules;

import com.portfolio.backend.entity.Project;
import com.portfolio.backend.entity.Technology;
import com.portfolio.backend.exception.BusinessException;
import com.portfolio.backend.exception.ResourceNotFoundException;
import com.portfolio.backend.repository.ProjectRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class ProjectBusinessRules {

    private final ProjectRepository projectRepository;
    private final TechnologyBusinessRules technologyBusinessRules;

    public ProjectBusinessRules(
        ProjectRepository projectRepository,
        TechnologyBusinessRules technologyBusinessRules
    ) {
        this.projectRepository = projectRepository;
        this.technologyBusinessRules = technologyBusinessRules;
    }

    public Project getByIdOrThrow(UUID id) {
        return projectRepository.findByIdWithTranslationsAndTechnologies(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found: " + id));
    }

    public void checkExistsOrThrow(UUID id) {
        if (!projectRepository.existsById(id)) {
            throw new ResourceNotFoundException("Project not found: " + id);
        }
    }

    public List<Project> getAllWithTranslations() {
        return projectRepository.findAllWithTranslations();
    }

    public List<Project> getFeaturedWithTranslations() {
        return projectRepository.findFeaturedWithTranslations();
    }

    public List<Technology> getTechnologiesByIds(List<UUID> technologyIds) {
        return technologyBusinessRules.getByIds(technologyIds);
    }

    public void checkDuplicateTitleOrThrow(String titleTr) {
        if (projectRepository.existsByTrTitle(titleTr)) {
            throw new BusinessException("A project with this title already exists: " + titleTr);
        }
    }

    public void checkDuplicateTitleExcludingOrThrow(String titleTr, UUID excludeId) {
        if (projectRepository.existsByTrTitleExcluding(titleTr, excludeId)) {
            throw new BusinessException("A project with this title already exists: " + titleTr);
        }
    }
}
