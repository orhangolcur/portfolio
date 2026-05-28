package com.portfolio.backend.mapper;

import com.portfolio.backend.dto.workexperience.WorkExperienceRequest;
import com.portfolio.backend.dto.workexperience.WorkExperienceResponse;
import com.portfolio.backend.entity.WorkExperience;
import com.portfolio.backend.entity.WorkExperienceTranslation;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class WorkExperienceMapper {

    public WorkExperienceResponse toResponse(WorkExperience workExperience) {
        String positionTr = getPositionByLocale(workExperience, "tr");
        String positionEn = getPositionByLocale(workExperience, "en");
        String descriptionTr = getDescriptionByLocale(workExperience, "tr");
        String descriptionEn = getDescriptionByLocale(workExperience, "en");

        return WorkExperienceResponse.builder()
                .id(workExperience.getId())
                .company(workExperience.getCompany())
                .startDate(workExperience.getStartDate())
                .endDate(workExperience.getEndDate())
                .current(workExperience.isCurrent())
                .positionTr(positionTr)
                .positionEn(positionEn)
                .descriptionTr(descriptionTr)
                .descriptionEn(descriptionEn)
                .build();
    }

    public List<WorkExperienceResponse> toResponseList(List<WorkExperience> workExperiences) {
        return workExperiences.stream()
                .map(this::toResponse)
                .toList();
    }

    public WorkExperience toEntity(WorkExperienceRequest request) {
        WorkExperience workExperience = WorkExperience.builder()
                .company(request.getCompany())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .isCurrent(request.isCurrent())
                .build();

        WorkExperienceTranslation trTranslation = WorkExperienceTranslation.builder()
                .workExperience(workExperience)
                .locale("tr")
                .position(request.getPositionTr())
                .description(request.getDescriptionTr())
                .build();

        WorkExperienceTranslation enTranslation = WorkExperienceTranslation.builder()
                .workExperience(workExperience)
                .locale("en")
                .position(request.getPositionEn())
                .description(request.getDescriptionEn())
                .build();

        workExperience.getTranslations().add(trTranslation);
        workExperience.getTranslations().add(enTranslation);

        return workExperience;
    }

    public void updateTranslation(WorkExperience workExperience, String locale,
                                   String position, String description) {
        workExperience.getTranslations().stream()
                .filter(t -> t.getLocale().equals(locale))
                .findFirst()
                .ifPresentOrElse(
                        t -> {
                            t.setPosition(position);
                            t.setDescription(description);
                        },
                        () -> workExperience.getTranslations().add(
                                WorkExperienceTranslation.builder()
                                        .workExperience(workExperience)
                                        .locale(locale)
                                        .position(position)
                                        .description(description)
                                        .build()
                        )
                );
    }

    public void updateEntity(WorkExperience workExperience, WorkExperienceRequest request) {
        workExperience.setCompany(request.getCompany());
        workExperience.setStartDate(request.getStartDate());
        workExperience.setEndDate(request.getEndDate());
        workExperience.setCurrent(request.isCurrent());
    }

    private String getPositionByLocale(WorkExperience workExperience, String locale) {
        return workExperience.getTranslations().stream()
                .filter(t -> t.getLocale().equals(locale))
                .findFirst()
                .map(WorkExperienceTranslation::getPosition)
                .orElse("");
    }

    private String getDescriptionByLocale(WorkExperience workExperience, String locale) {
        return workExperience.getTranslations().stream()
                .filter(t -> t.getLocale().equals(locale))
                .findFirst()
                .map(WorkExperienceTranslation::getDescription)
                .orElse("");
    }
}