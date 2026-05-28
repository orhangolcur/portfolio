package com.portfolio.backend.service.rules;

import com.portfolio.backend.entity.Technology;
import com.portfolio.backend.exception.ResourceNotFoundException;
import com.portfolio.backend.repository.TechnologyRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class TechnologyBusinessRules {

    private final TechnologyRepository technologyRepository;

    public TechnologyBusinessRules(TechnologyRepository technologyRepository) {
        this.technologyRepository = technologyRepository;
    }

    public List<Technology> getAll() {
        return technologyRepository.findAll();
    }

    public Technology getByIdOrThrow(UUID id) {
        return technologyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Technology not found."));
    }

    public void checkExistsOrThrow(UUID id) {
        if (!technologyRepository.existsById(id)) {
            throw new ResourceNotFoundException("Technology not found.");
        }
    }

    public List<Technology> getByIds(List<UUID> ids) {
        List<UUID> distinctIds = ids.stream().distinct().toList();
        List<Technology> found = technologyRepository.findAllById(distinctIds);
        if (found.size() != distinctIds.size()) {
            throw new ResourceNotFoundException("One or more technologies not found.");
        }
        return found;
    }
}