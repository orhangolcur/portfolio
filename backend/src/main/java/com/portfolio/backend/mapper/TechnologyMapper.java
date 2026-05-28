package com.portfolio.backend.mapper;

import com.portfolio.backend.dto.technology.TechnologyRequest;
import com.portfolio.backend.dto.technology.TechnologyResponse;
import com.portfolio.backend.entity.Technology;
import org.springframework.stereotype.Component;
import java.util.Collection;
import java.util.List;

@Component
public class TechnologyMapper {

    public TechnologyResponse toResponse(Technology technology) {
        return TechnologyResponse.builder()
                .id(technology.getId())
                .name(technology.getName())
                .iconUrl(technology.getIconUrl())
                .category(technology.getCategory())
                .build();
    }

    public List<TechnologyResponse> toResponseList(Collection<Technology> technologies) {
        return technologies.stream()
                .map(this::toResponse)
                .toList();
    }

    public Technology toEntity(TechnologyRequest request) {
        return Technology.builder()
                .name(request.getName())
                .iconUrl(request.getIconUrl())
                .category(request.getCategory())
                .build();
    }

    public void updateTechnologyFields(Technology technology, TechnologyRequest request) {
        technology.setName(request.getName());
        technology.setIconUrl(request.getIconUrl());
        technology.setCategory(request.getCategory());
    }
}