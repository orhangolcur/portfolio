package com.portfolio.backend.service.rules;

import com.portfolio.backend.dto.education.EducationRequest;
import com.portfolio.backend.entity.Education;
import com.portfolio.backend.exception.BusinessException;
import com.portfolio.backend.exception.ResourceNotFoundException;
import com.portfolio.backend.repository.EducationRepository;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.UUID;

@Component
public class EducationBusinessRules {

    private final EducationRepository educationRepository;

    public EducationBusinessRules(EducationRepository educationRepository) {
        this.educationRepository = educationRepository;
    }

    public Education getByIdOrThrow(UUID id) {
        return educationRepository.findByIdWithTranslations(id)
                .orElseThrow(() -> new ResourceNotFoundException("Education not found: " + id));
    }

    public List<Education> getAllWithTranslations() {
        return educationRepository.findAllWithTranslations();
    }

    public void checkExistsOrThrow(UUID id) {
        if (!educationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Education not found: " + id);
        }
    }

    public void validateDatesOrThrow(EducationRequest request) {
        if (request.getEndDate() != null
                && !request.getEndDate().isAfter(request.getStartDate())) {
            throw new BusinessException("End date must be after start date.");
        }
    }
}
