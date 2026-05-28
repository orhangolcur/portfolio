package com.portfolio.backend.mapper;

import com.portfolio.backend.dto.education.EducationRequest;
import com.portfolio.backend.dto.education.EducationResponse;
import com.portfolio.backend.entity.Education;
import com.portfolio.backend.entity.EducationTranslation;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class EducationMapper {

    public EducationResponse toResponse(Education education) {
        return EducationResponse.builder()
                .id(education.getId())
                .startDate(education.getStartDate())
                .endDate(education.getEndDate())
                .institutionTr(getInstitutionByLocale(education, "tr"))
                .institutionEn(getInstitutionByLocale(education, "en"))
                .fieldOfStudyTr(getFieldOfStudyByLocale(education, "tr"))
                .fieldOfStudyEn(getFieldOfStudyByLocale(education, "en"))
                .degreeTr(getDegreeByLocale(education, "tr"))
                .degreeEn(getDegreeByLocale(education, "en"))
                .build();
    }

    public List<EducationResponse> toResponseList(List<Education> educations) {
        return educations.stream()
                .map(this::toResponse)
                .toList();
    }

    public Education toEntity(EducationRequest request) {
        Education education = Education.builder()
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .build();

        EducationTranslation trTranslation = EducationTranslation.builder()
                .education(education)
                .locale("tr")
                .institution(request.getInstitutionTr())
                .fieldOfStudy(request.getFieldOfStudyTr())
                .degree(request.getDegreeTr())
                .build();

        EducationTranslation enTranslation = EducationTranslation.builder()
                .education(education)
                .locale("en")
                .institution(request.getInstitutionEn())
                .fieldOfStudy(request.getFieldOfStudyEn())
                .degree(request.getDegreeEn())
                .build();

        education.getTranslations().add(trTranslation);
        education.getTranslations().add(enTranslation);

        return education;
    }

    public void updateEntity(Education education, EducationRequest request) {
        education.setStartDate(request.getStartDate());
        education.setEndDate(request.getEndDate());
    }

    public void updateTranslation(Education education, String locale,
                                   String institution, String fieldOfStudy, String degree) {
        education.getTranslations().stream()
                .filter(t -> t.getLocale().equals(locale))
                .findFirst()
                .ifPresentOrElse(
                        t -> {
                            t.setInstitution(institution);
                            t.setFieldOfStudy(fieldOfStudy);
                            t.setDegree(degree);
                        },
                        () -> education.getTranslations().add(
                                EducationTranslation.builder()
                                        .education(education)
                                        .locale(locale)
                                        .institution(institution)
                                        .fieldOfStudy(fieldOfStudy)
                                        .degree(degree)
                                        .build()
                        )
                );
    }

    private String getInstitutionByLocale(Education education, String locale) {
        return education.getTranslations().stream()
                .filter(t -> t.getLocale().equals(locale))
                .findFirst()
                .map(EducationTranslation::getInstitution)
                .orElse("");
    }

    private String getFieldOfStudyByLocale(Education education, String locale) {
        return education.getTranslations().stream()
                .filter(t -> t.getLocale().equals(locale))
                .findFirst()
                .map(EducationTranslation::getFieldOfStudy)
                .orElse("");
    }

    private String getDegreeByLocale(Education education, String locale) {
        return education.getTranslations().stream()
                .filter(t -> t.getLocale().equals(locale))
                .findFirst()
                .map(EducationTranslation::getDegree)
                .orElse("");
    }
}
