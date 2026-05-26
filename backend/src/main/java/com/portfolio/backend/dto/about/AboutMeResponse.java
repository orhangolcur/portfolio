package com.portfolio.backend.dto.about;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AboutMeResponse {
    private String bio; // o anki dilde biyografi
    private String profileImageUrl;
    private String githubUrl;
    private String linkedinUrl;
    private String cvUrl;
}
