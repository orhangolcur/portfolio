package com.portfolio.backend.dto.education;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EducationRequest {

    @NotNull(message = "Start date is required.")
    @PastOrPresent(message = "Start date cannot be in the future.")
    private LocalDate startDate;

    private LocalDate endDate;

    @NotBlank(message = "Turkish institution name is required.")
    @Size(max = 255, message = "Turkish institution name must be less than 255 characters.")
    private String institutionTr;

    @NotBlank(message = "English institution name is required.")
    @Size(max = 255, message = "English institution name must be less than 255 characters.")
    private String institutionEn;

    @NotBlank(message = "Turkish field of study is required.")
    @Size(max = 255, message = "Turkish field of study must be less than 255 characters.")
    private String fieldOfStudyTr;

    @NotBlank(message = "English field of study is required.")
    @Size(max = 255, message = "English field of study must be less than 255 characters.")
    private String fieldOfStudyEn;

    @NotBlank(message = "Turkish degree is required.")
    @Size(max = 255, message = "Turkish degree must be less than 255 characters.")
    private String degreeTr;

    @NotBlank(message = "English degree is required.")
    @Size(max = 255, message = "English degree must be less than 255 characters.")
    private String degreeEn;
}
