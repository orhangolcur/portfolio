package com.portfolio.backend.dto.workexperience;

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
public class WorkExperienceRequest {

    @NotBlank(message = "Company name is required.")
    @Size(max = 255, message = "Company name must be less than 255 characters.")
    private String company;

    @NotNull(message = "Start date is required.")
    @PastOrPresent(message = "Start date cannot be in the future.")
    private LocalDate startDate;

    private LocalDate endDate;

    private boolean current;

    @NotBlank(message = "Turkish position is required.")
    @Size(max = 255, message = "Turkish position must be less than 255 characters.")
    private String positionTr;

    @NotBlank(message = "English position is required.")
    @Size(max = 255, message = "English position must be less than 255 characters.")
    private String positionEn;

    @Size(max = 2000, message = "Turkish description must be less than 2000 characters.")
    private String descriptionTr;

    @Size(max = 2000, message = "English description must be less than 2000 characters.")
    private String descriptionEn;
}