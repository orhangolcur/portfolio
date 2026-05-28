package com.portfolio.backend.dto.education;

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
public class EducationResponse {

    private UUID id;
    private LocalDate startDate;
    private LocalDate endDate;
    private String institutionTr;
    private String institutionEn;
    private String fieldOfStudyTr;
    private String fieldOfStudyEn;
    private String degreeTr;
    private String degreeEn;
}
