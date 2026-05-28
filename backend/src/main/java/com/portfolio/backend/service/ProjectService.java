package com.portfolio.backend.service;

import com.portfolio.backend.dto.project.ProjectRequest;
import com.portfolio.backend.dto.project.ProjectResponse;
import com.portfolio.backend.entity.Project;
import com.portfolio.backend.entity.Technology;
import com.portfolio.backend.mapper.ProjectMapper;
import com.portfolio.backend.repository.ProjectRepository;
import com.portfolio.backend.service.rules.ProjectBusinessRules;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectBusinessRules projectBusinessRules;
    private final ProjectMapper projectMapper;

    public ProjectService(
        ProjectRepository projectRepository,
        ProjectBusinessRules projectBusinessRules,
        ProjectMapper projectMapper
    ) {
        this.projectRepository = projectRepository;
        this.projectBusinessRules = projectBusinessRules;
        this.projectMapper = projectMapper;
    }

    @Transactional(readOnly = true)
    public List<ProjectResponse> getAll() {
        return projectMapper.toResponseList(projectBusinessRules.getAllWithTranslations());
    }

    @Transactional(readOnly = true)
    public List<ProjectResponse> getFeatured() {
        return projectMapper.toResponseList(projectBusinessRules.getFeaturedWithTranslations());
    }

    @Transactional(readOnly = true)
    public ProjectResponse getById(UUID id) {
        return projectMapper.toResponse(projectBusinessRules.getByIdOrThrow(id));
    }

    @Transactional
    public ProjectResponse create(ProjectRequest request) {
        projectBusinessRules.checkDuplicateTitleOrThrow(request.getTitleTr());
        List<Technology> technologies = projectBusinessRules.getTechnologiesByIds(request.getTechnologyIds());
        Project saved = projectRepository.save(projectMapper.toEntity(request, technologies));
        return projectMapper.toResponse(saved);
    }

    @Transactional
    public ProjectResponse update(UUID id, ProjectRequest request) {
        Project project = projectBusinessRules.getByIdOrThrow(id);
        projectBusinessRules.checkDuplicateTitleExcludingOrThrow(request.getTitleTr(), id);
        List<Technology> technologies = projectBusinessRules.getTechnologiesByIds(request.getTechnologyIds());

        project.setGithubUrl(request.getGithubUrl());
        project.setLiveUrl(request.getLiveUrl());
        project.setImageUrl(request.getImageUrl());
        project.setFeatured(request.isFeatured());
        project.setDisplayOrder(request.getDisplayOrder());

        projectMapper.updateTranslation(project, "tr", request.getTitleTr(), request.getDescriptionTr());
        projectMapper.updateTranslation(project, "en", request.getTitleEn(), request.getDescriptionEn());

        project.getTechnologies().clear();
        project.getTechnologies().addAll(technologies);

        return projectMapper.toResponse(projectRepository.save(project));
    }

    @Transactional
    public void delete(UUID id) {
        projectBusinessRules.checkExistsOrThrow(id);
        projectRepository.deleteById(id);
    }
}
