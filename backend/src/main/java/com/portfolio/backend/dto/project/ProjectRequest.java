package com.portfolio.backend.dto.project;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;
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
public class ProjectRequest {

    @NotBlank(message = "Turkish title is required.")
    @Size(max = 200, message = "Turkish title must be less than 200 characters.")
    private String titleTr;

    @NotBlank(message = "English title is required.")
    @Size(max = 200, message = "English title must be less than 200 characters.")
    private String titleEn;

    @NotBlank(message = "Turkish description is required.")
    @Size(max = 2000, message = "Turkish description must be less than 2000 characters.")
    private String descriptionTr;

    @NotBlank(message = "English description is required.")
    @Size(max = 2000, message = "English description must be less than 2000 characters.")
    private String descriptionEn;

    @URL(message = "GitHub URL must be a valid URL.")
    @Size(max = 500, message = "GitHub URL must be less than 500 characters.")
    private String githubUrl;

    @URL(message = "Live site URL must be a valid URL.")
    @Size(max = 500, message = "Live site URL must be less than 500 characters.")
    private String liveUrl;

    @URL(message = "Image URL must be a valid URL.")
    @Size(max = 500, message = "Image URL must be less than 500 characters.")
    private String imageUrl;

    private boolean featured;

    @Min(value = 0, message = "Display order must be 0 or greater.")
    private int displayOrder;

    @NotNull(message = "Technology list must not be null.")
    @Size(min = 1, message = "At least one technology is required.")
    private List<UUID> technologyIds;
}
