package com.portfolio.backend.dto.about;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AboutMeRequest {
    private String bioTr;
    private String bioEn;
    private String profileImageUrl;
    private String githubUrl;
    private String linkedinUrl;
    private String cvUrl;
}
