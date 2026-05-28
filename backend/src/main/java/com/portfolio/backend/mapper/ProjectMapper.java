package com.portfolio.backend.mapper;

import com.portfolio.backend.dto.project.ProjectRequest;
import com.portfolio.backend.dto.project.ProjectResponse;
import com.portfolio.backend.entity.Project;
import com.portfolio.backend.entity.ProjectTranslation;
import com.portfolio.backend.entity.Technology;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class ProjectMapper { 

    private final TechnologyMapper technologyMapper;

    public ProjectMapper(TechnologyMapper technologyMapper) {
        this.technologyMapper = technologyMapper;
    }

    // Entity + locale → Response
    public ProjectResponse toResponse(Project project, String locale) {
        Optional<ProjectTranslation> translation = project.getTranslations().stream()
                .filter(t -> t.getLocale().equals(locale))
                .findFirst();

        String title = translation.map(ProjectTranslation::getTitle).orElse("");
        String description = translation.map(ProjectTranslation::getDescription).orElse("");

        return ProjectResponse.builder()
                .id(project.getId())
                .title(title)
                .description(description)
                .githubUrl(project.getGithubUrl())
                .liveUrl(project.getLiveUrl())
                .imageUrl(project.getImageUrl())
                .featured(project.isFeatured())
                .displayOrder(project.getDisplayOrder())
                .technologies(technologyMapper.toResponseList(project.getTechnologies()))
                .build();
    }

    // Entity listesi + locale → Response listesi
    public List<ProjectResponse> toResponseList(List<Project> projects, String locale) {
        return projects.stream()
                .map(project -> toResponse(project, locale))
                .toList();
    }

    // Request → yeni Entity
    public Project toEntity(ProjectRequest request, List<Technology> technologies) {
        Project project = Project.builder()
                .githubUrl(request.getGithubUrl())
                .liveUrl(request.getLiveUrl())
                .imageUrl(request.getImageUrl())
                .featured(request.isFeatured())
                .displayOrder(request.getDisplayOrder())
                .build();

        // Çevirileri ekle
        ProjectTranslation trTranslation = ProjectTranslation.builder()
                .project(project)
                .locale("tr")
                .title(request.getTitleTr())
                .description(request.getDescriptionTr())
                .build();

        ProjectTranslation enTranslation = ProjectTranslation.builder()
                .project(project)
                .locale("en")
                .title(request.getTitleEn())
                .description(request.getDescriptionEn())
                .build();

        project.getTranslations().add(trTranslation);
        project.getTranslations().add(enTranslation);
        project.getTechnologies().addAll(technologies);

        return project;
    }

    // Mevcut entity'yi güncelle
    public void updateTranslation(Project project, String locale, String title, String description) {
        project.getTranslations().stream()
                .filter(t -> t.getLocale().equals(locale))
                .findFirst()
                .ifPresentOrElse(
                        t -> {
                            t.setTitle(title);
                            t.setDescription(description);
                        },
                        () -> project.getTranslations().add(
                                ProjectTranslation.builder()
                                        .project(project)
                                        .locale(locale)
                                        .title(title)
                                        .description(description)
                                        .build()
                        )
                );
    }
}