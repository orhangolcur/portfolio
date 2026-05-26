package com.portfolio.backend.mapper;

import com.portfolio.backend.dto.about.AboutMeRequest;
import com.portfolio.backend.dto.about.AboutMeResponse;
import com.portfolio.backend.entity.AboutMe;
import com.portfolio.backend.entity.AboutMeTranslation;
import org.springframework.stereotype.Component;

@Component
public class AboutMeMapper {

    // Entity + locale → Response
    public AboutMeResponse toResponse(AboutMe aboutMe, String locale) {
        // istenilen dile ait bio'yu bul
        String bio = aboutMe.getTranslations().stream()
                .filter(t -> t.getLocale().equals(locale))
                .findFirst()
                .map(AboutMeTranslation::getBio)
                .orElse("");

        return AboutMeResponse.builder()
                .bio(bio)
                .profileImageUrl(aboutMe.getProfileImageUrl())
                .githubUrl(aboutMe.getGithubUrl())
                .linkedinUrl(aboutMe.getLinkedinUrl())
                .cvUrl(aboutMe.getCvUrl())
                .build();
    }

    // Request → Translation entity
    // yeni çeviri kaydı oluşturulurken kullanılır
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
}