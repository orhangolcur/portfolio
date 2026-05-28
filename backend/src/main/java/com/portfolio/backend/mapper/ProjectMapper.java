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

    public ProjectResponse toResponse(Project project) {
        Optional<ProjectTranslation> tr = getTranslation(project, "tr");
        Optional<ProjectTranslation> en = getTranslation(project, "en");

        return ProjectResponse.builder()
                .id(project.getId())
                .titleTr(tr.map(ProjectTranslation::getTitle).orElse(""))
                .titleEn(en.map(ProjectTranslation::getTitle).orElse(""))
                .descriptionTr(tr.map(ProjectTranslation::getDescription).orElse(""))
                .descriptionEn(en.map(ProjectTranslation::getDescription).orElse(""))
                .githubUrl(project.getGithubUrl())
                .liveUrl(project.getLiveUrl())
                .imageUrl(project.getImageUrl())
                .featured(project.isFeatured())
                .displayOrder(project.getDisplayOrder())
                .technologies(technologyMapper.toResponseList(project.getTechnologies()))
                .build();
    }

    public List<ProjectResponse> toResponseList(List<Project> projects) {
        return projects.stream()
                .map(this::toResponse)
                .toList();
    }

    public Project toEntity(ProjectRequest request, List<Technology> technologies) {
        Project project = Project.builder()
                .githubUrl(request.getGithubUrl())
                .liveUrl(request.getLiveUrl())
                .imageUrl(request.getImageUrl())
                .featured(request.isFeatured())
                .displayOrder(request.getDisplayOrder())
                .build();

        project.getTranslations().add(ProjectTranslation.builder()
                .project(project).locale("tr")
                .title(request.getTitleTr()).description(request.getDescriptionTr())
                .build());

        project.getTranslations().add(ProjectTranslation.builder()
                .project(project).locale("en")
                .title(request.getTitleEn()).description(request.getDescriptionEn())
                .build());

        project.getTechnologies().addAll(technologies);

        return project;
    }

    public void updateTranslation(Project project, String locale, String title, String description) {
        project.getTranslations().stream()
                .filter(t -> t.getLocale().equals(locale))
                .findFirst()
                .ifPresentOrElse(
                        t -> { t.setTitle(title); t.setDescription(description); },
                        () -> project.getTranslations().add(
                                ProjectTranslation.builder()
                                        .project(project).locale(locale)
                                        .title(title).description(description)
                                        .build()
                        )
                );
    }

    private Optional<ProjectTranslation> getTranslation(Project project, String locale) {
        return project.getTranslations().stream()
                .filter(t -> t.getLocale().equals(locale))
                .findFirst();
    }
}
