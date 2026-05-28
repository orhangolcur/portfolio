package com.portfolio.backend.service.rules;

import com.portfolio.backend.dto.workexperience.WorkExperienceRequest;
import com.portfolio.backend.entity.WorkExperience;
import com.portfolio.backend.exception.BusinessException;
import com.portfolio.backend.exception.ResourceNotFoundException;
import com.portfolio.backend.repository.WorkExperienceRepository;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.UUID;

@Component
public class WorkExperienceBusinessRules {

    private final WorkExperienceRepository workExperienceRepository;

    public WorkExperienceBusinessRules(WorkExperienceRepository workExperienceRepository) {
        this.workExperienceRepository = workExperienceRepository;
    }

    public WorkExperience getByIdOrThrow(UUID id) {
        return workExperienceRepository.findByIdWithTranslations(id)
                .orElseThrow(() -> new ResourceNotFoundException("Work experience not found: " + id));
    }

    public List<WorkExperience> getAllWithTranslations() {
        return workExperienceRepository.findAllWithTranslations();
    }

    public void checkExistsOrThrow(UUID id) {
        if (!workExperienceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Work experience not found: " + id);
        }
    }

    public void validateDatesOrThrow(WorkExperienceRequest request) {
        if (request.isCurrent() && request.getEndDate() != null) {
            throw new BusinessException("End date must be null when the position is current.");
        }
        if (!request.isCurrent() && request.getEndDate() != null
                && !request.getEndDate().isAfter(request.getStartDate())) {
            throw new BusinessException("End date must be after start date.");
        }
    }
}