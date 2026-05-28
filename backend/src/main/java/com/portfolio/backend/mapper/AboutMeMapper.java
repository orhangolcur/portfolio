package com.portfolio.backend.mapper;

import com.portfolio.backend.dto.about.AboutMeRequest;
import com.portfolio.backend.dto.about.AboutMeResponse;
import com.portfolio.backend.entity.AboutMe;
import com.portfolio.backend.entity.AboutMeTranslation;
import org.springframework.stereotype.Component;

@Component
public class AboutMeMapper {

    public AboutMeResponse toResponse(AboutMe aboutMe) {
        return AboutMeResponse.builder()
                .bioTr(getBioByLocale(aboutMe, "tr"))
                .bioEn(getBioByLocale(aboutMe, "en"))
                .profileImageUrl(aboutMe.getProfileImageUrl())
                .githubUrl(aboutMe.getGithubUrl())
                .linkedinUrl(aboutMe.getLinkedinUrl())
                .cvUrl(aboutMe.getCvUrl())
                .build();
    }

    public AboutMeTranslation toTranslation(AboutMe aboutMe, String locale, String bio) {
        return AboutMeTranslation.builder()
                .aboutMe(aboutMe)
                .locale(locale)
                .bio(bio)
                .build();
    }

    public void updateAboutMeFields(AboutMe aboutMe, AboutMeRequest request) {
        aboutMe.setProfileImageUrl(request.getProfileImageUrl());
        aboutMe.setGithubUrl(request.getGithubUrl());
        aboutMe.setLinkedinUrl(request.getLinkedinUrl());
        aboutMe.setCvUrl(request.getCvUrl());
    }

    private String getBioByLocale(AboutMe aboutMe, String locale) {
        return aboutMe.getTranslations().stream()
                .filter(t -> t.getLocale().equals(locale))
                .findFirst()
                .map(AboutMeTranslation::getBio)
                .orElse("");
    }
}
