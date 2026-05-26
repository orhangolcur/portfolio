package com.portfolio.backend.dto.technology;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TechnologyRequest {
    private String name;
    private String iconUrl;
    private String category;
}