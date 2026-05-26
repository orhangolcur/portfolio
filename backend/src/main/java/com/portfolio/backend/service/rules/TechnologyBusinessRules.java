package com.portfolio.backend.service.rules;

import com.portfolio.backend.entity.Technology;
import com.portfolio.backend.repository.TechnologyRepository;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.UUID;

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
                .orElseThrow(() -> new RuntimeException("Technology not found"));
    }
}