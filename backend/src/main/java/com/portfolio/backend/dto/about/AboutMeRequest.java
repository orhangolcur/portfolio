package com.portfolio.backend.dto.about;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AboutMeRequest {
    @NotBlank(message = "Turkish bio is required.")
    @Size(max = 2000, message = "Turkish bio must be less than 2000 characters.")
    private String bioTr;

    @NotBlank(message = "English bio is required.")
    @Size(max = 2000, message = "English bio must be less than 2000 characters.")
    private String bioEn;

    @URL(message = "Profile image URL must be a valid URL.")
    @Size(min = 1, max = 500, message = "Profile image URL must be less than 500 characters.")
    private String profileImageUrl;

    @URL(message = "GitHub URL must be a valid URL.")
    @Size(min = 1, max = 500, message = "GitHub URL must be less than 500 characters.")
    private String githubUrl;

    @URL(message = "LinkedIn URL must be a valid URL.")
    @Size(min = 1, max = 500, message = "LinkedIn URL must be less than 500 characters.")
    private String linkedinUrl;

    @URL(message = "CV URL must be a valid URL.")
    @Size(min = 1, max = 500, message = "CV URL must be less than 500 characters.")
    private String cvUrl;
}
