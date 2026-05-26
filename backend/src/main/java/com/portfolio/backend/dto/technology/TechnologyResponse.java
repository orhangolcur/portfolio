package com.portfolio.backend.dto.technology;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TechnologyResponse {
    private UUID id;
    private String name;
    private String iconUrl;
    private String category;
}