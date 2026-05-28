package com.portfolio.backend.dto.project;

import com.portfolio.backend.dto.technology.TechnologyResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectResponse {
    private UUID id;
    private String titleTr;
    private String titleEn;
    private String descriptionTr;
    private String descriptionEn;
    private String githubUrl;
    private String liveUrl;
    private String imageUrl;
    private boolean featured;
    private int displayOrder;
    private List<TechnologyResponse> technologies;
}