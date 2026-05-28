package com.portfolio.backend.service;

import com.portfolio.backend.dto.education.EducationRequest;
import com.portfolio.backend.dto.education.EducationResponse;
import com.portfolio.backend.entity.Education;
import com.portfolio.backend.mapper.EducationMapper;
import com.portfolio.backend.repository.EducationRepository;
import com.portfolio.backend.service.rules.EducationBusinessRules;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class EducationService {

    private final EducationRepository educationRepository;
    private final EducationBusinessRules educationBusinessRules;
    private final EducationMapper educationMapper;

    public EducationService(
            EducationRepository educationRepository,
            EducationBusinessRules educationBusinessRules,
            EducationMapper educationMapper) {
        this.educationRepository = educationRepository;
        this.educationBusinessRules = educationBusinessRules;
        this.educationMapper = educationMapper;
    }

    @Transactional(readOnly = true)
    public List<EducationResponse> getAll() {
        List<Education> educations = educationBusinessRules.getAllWithTranslations();
        return educationMapper.toResponseList(educations);
    }

    @Transactional(readOnly = true)
    public EducationResponse getById(UUID id) {
        Education education = educationBusinessRules.getByIdOrThrow(id);
        return educationMapper.toResponse(education);
    }

    @Transactional
    public EducationResponse create(EducationRequest request) {
        educationBusinessRules.validateDatesOrThrow(request);
        Education saved = educationRepository.save(educationMapper.toEntity(request));
        return educationMapper.toResponse(saved);
    }

    @Transactional
    public EducationResponse update(UUID id, EducationRequest request) {
        educationBusinessRules.validateDatesOrThrow(request);
        Education education = educationBusinessRules.getByIdOrThrow(id);
        educationMapper.updateEntity(education, request);
        educationMapper.updateTranslation(education, "tr", request.getInstitutionTr(), request.getFieldOfStudyTr(), request.getDegreeTr());
        educationMapper.updateTranslation(education, "en", request.getInstitutionEn(), request.getFieldOfStudyEn(), request.getDegreeEn());
        return educationMapper.toResponse(educationRepository.save(education));
    }

    @Transactional
    public void delete(UUID id) {
        educationBusinessRules.checkExistsOrThrow(id);
        educationRepository.deleteById(id);
    }
}
