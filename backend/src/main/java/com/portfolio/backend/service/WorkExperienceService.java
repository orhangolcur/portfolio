package com.portfolio.backend.service;

import com.portfolio.backend.dto.workexperience.WorkExperienceRequest;
import com.portfolio.backend.dto.workexperience.WorkExperienceResponse;
import com.portfolio.backend.entity.WorkExperience;
import com.portfolio.backend.mapper.WorkExperienceMapper;
import com.portfolio.backend.repository.WorkExperienceRepository;
import com.portfolio.backend.service.rules.WorkExperienceBusinessRules;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class WorkExperienceService {

    private final WorkExperienceRepository workExperienceRepository;
    private final WorkExperienceBusinessRules workExperienceBusinessRules;
    private final WorkExperienceMapper workExperienceMapper;

    public WorkExperienceService(
            WorkExperienceRepository workExperienceRepository,
            WorkExperienceBusinessRules workExperienceBusinessRules,
            WorkExperienceMapper workExperienceMapper) {
        this.workExperienceRepository = workExperienceRepository;
        this.workExperienceBusinessRules = workExperienceBusinessRules;
        this.workExperienceMapper = workExperienceMapper;
    }

    @Transactional(readOnly = true)
    public List<WorkExperienceResponse> getAll() {
        List<WorkExperience> workExperiences = workExperienceBusinessRules.getAllWithTranslations();
        return workExperienceMapper.toResponseList(workExperiences);
    }

    @Transactional(readOnly = true)
    public WorkExperienceResponse getById(UUID id) {
        WorkExperience workExperience = workExperienceBusinessRules.getByIdOrThrow(id);
        return workExperienceMapper.toResponse(workExperience);
    }

    @Transactional
    public WorkExperienceResponse create(WorkExperienceRequest request) {
        workExperienceBusinessRules.validateDatesOrThrow(request);
        WorkExperience saved = workExperienceRepository.save(workExperienceMapper.toEntity(request));
        return workExperienceMapper.toResponse(saved);
    }

    @Transactional
    public WorkExperienceResponse update(UUID id, WorkExperienceRequest request) {
        workExperienceBusinessRules.validateDatesOrThrow(request);
        WorkExperience workExperience = workExperienceBusinessRules.getByIdOrThrow(id);
        workExperienceMapper.updateEntity(workExperience, request);
        workExperienceMapper.updateTranslation(workExperience, "tr", request.getPositionTr(), request.getDescriptionTr());
        workExperienceMapper.updateTranslation(workExperience, "en", request.getPositionEn(), request.getDescriptionEn());
        return workExperienceMapper.toResponse(workExperienceRepository.save(workExperience));
    }

    @Transactional
    public void delete(UUID id) {
        workExperienceBusinessRules.checkExistsOrThrow(id);
        workExperienceRepository.deleteById(id);
    }
}