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
    public List<ProjectResponse> getAll(String locale) {
        List<Project> projects = projectBusinessRules.getAllWithTranslations();
        return projectMapper.toResponseList(projects, locale);
    }

    @Transactional(readOnly = true)
    public List<ProjectResponse> getFeatured(String locale) {
        List<Project> projects = projectBusinessRules.getFeaturedWithTranslations();
        return projectMapper.toResponseList(projects, locale);
    }

    @Transactional(readOnly = true)
    public ProjectResponse getById(UUID id, String locale) {
        Project project = projectBusinessRules.getByIdOrThrow(id);
        return projectMapper.toResponse(project, locale);
    }

    @Transactional
    public ProjectResponse create(ProjectRequest request, String locale) {
        projectBusinessRules.checkDuplicateTitleOrThrow(request.getTitleTr());
        List<Technology> technologies = projectBusinessRules.getTechnologiesByIds(request.getTechnologyIds());
        Project project = projectMapper.toEntity(request, technologies);
        Project saved = projectRepository.save(project);
        return projectMapper.toResponse(saved, locale);
    }

    @Transactional
    public ProjectResponse update(UUID id, ProjectRequest request, String locale) {
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

        Project saved = projectRepository.save(project);
        return projectMapper.toResponse(saved, locale);
    }

    @Transactional
    public void delete(UUID id) {
        projectBusinessRules.checkExistsOrThrow(id);
        projectRepository.deleteById(id);
    }
}