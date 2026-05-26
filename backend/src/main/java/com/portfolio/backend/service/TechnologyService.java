package com.portfolio.backend.service;

import com.portfolio.backend.dto.technology.TechnologyRequest;
import com.portfolio.backend.dto.technology.TechnologyResponse;
import com.portfolio.backend.entity.Technology;
import com.portfolio.backend.mapper.TechnologyMapper;
import com.portfolio.backend.repository.TechnologyRepository;
import com.portfolio.backend.service.rules.TechnologyBusinessRules;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class TechnologyService {

    private final TechnologyRepository technologyRepository;
    private final TechnologyBusinessRules technologyBusinessRules;
    private final TechnologyMapper technologyMapper;

    public TechnologyService(
            TechnologyRepository technologyRepository,
            TechnologyBusinessRules technologyBusinessRules,
            TechnologyMapper technologyMapper
    ) {
        this.technologyRepository = technologyRepository;
        this.technologyBusinessRules = technologyBusinessRules;
        this.technologyMapper = technologyMapper;
    }

    @Transactional(readOnly = true)
    public List<TechnologyResponse> getAll() {
        return technologyMapper.toResponseList(technologyBusinessRules.getAll());
    }

    @Transactional(readOnly = true)
    public TechnologyResponse getById(UUID id) {
        Technology technology = technologyBusinessRules.getByIdOrThrow(id);
        return technologyMapper.toResponse(technology);
    }

    @Transactional
    public TechnologyResponse create(TechnologyRequest request) {
        Technology technology = technologyMapper.toEntity(request);
        Technology saved = technologyRepository.save(technology);
        return technologyMapper.toResponse(saved);
    }

    @Transactional
    public TechnologyResponse update(UUID id, TechnologyRequest request) {
        Technology technology = technologyBusinessRules.getByIdOrThrow(id);
        technologyMapper.updateTechnologyFields(technology, request);
        Technology saved = technologyRepository.save(technology);
        return technologyMapper.toResponse(saved);
    }

    @Transactional
    public void delete(UUID id) {
        technologyBusinessRules.getByIdOrThrow(id);
        technologyRepository.deleteById(id);
    }
}