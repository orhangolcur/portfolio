package com.portfolio.backend.dto.workexperience;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkExperienceResponse {
    private UUID id;
    private String company;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean current;
    private String positionTr;
    private String positionEn;
    private String descriptionTr;
    private String descriptionEn;
}