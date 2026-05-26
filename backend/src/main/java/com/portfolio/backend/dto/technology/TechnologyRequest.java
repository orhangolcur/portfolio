package com.portfolio.backend.dto.technology;

import org.hibernate.validator.constraints.URL;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TechnologyRequest {
    @NotBlank(message = "Technology name is required.")
    @Size(max = 100, message = "Technology name must be less than 100 characters.")
    private String name;

    @Size(max = 500, message = "Icon URL must be less than 500 characters.")
    @URL(message = "Icon URL must be a valid URL.")
    private String iconUrl;

    @Size(max = 100, message = "Category must be less than 100 characters.")
    private String category;
}