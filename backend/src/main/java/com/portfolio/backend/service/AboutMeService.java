package com.portfolio.backend.service;

import com.portfolio.backend.dto.about.AboutMeRequest;
import com.portfolio.backend.dto.about.AboutMeResponse;
import com.portfolio.backend.entity.AboutMe;
import com.portfolio.backend.mapper.AboutMeMapper;
import com.portfolio.backend.repository.AboutMeRepository;
import com.portfolio.backend.service.rules.AboutMeBusinessRules;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AboutMeService {

    private final AboutMeRepository aboutMeRepository;
    private final AboutMeBusinessRules aboutMeBusinessRules;
    private final AboutMeMapper aboutMeMapper;

    public AboutMeService(
        AboutMeRepository aboutMeRepository,
        AboutMeBusinessRules aboutMeBusinessRules,
        AboutMeMapper aboutMeMapper
    ) {
        this.aboutMeRepository = aboutMeRepository;
        this.aboutMeBusinessRules = aboutMeBusinessRules;
        this.aboutMeMapper = aboutMeMapper;
    }

    @Transactional(readOnly = true)
    public AboutMeResponse getAboutMe() {
        return aboutMeMapper.toResponse(aboutMeBusinessRules.getAboutMeOrThrow());
    }

    @Transactional
    public void updateAboutMe(AboutMeRequest request) {
        AboutMe aboutMe = aboutMeBusinessRules.getOrCreate();

        aboutMeMapper.updateAboutMeFields(aboutMe, request);

        updateTranslation(aboutMe, "tr", request.getBioTr());
        updateTranslation(aboutMe, "en", request.getBioEn());

        aboutMeRepository.save(aboutMe);
    }

    private void updateTranslation(AboutMe aboutMe, String locale, String bio) {
        aboutMe.getTranslations().stream()
                .filter(t -> t.getLocale().equals(locale))
                .findFirst()
                .ifPresentOrElse(
                        t -> t.setBio(bio),
                        () -> aboutMe.getTranslations().add(
                                aboutMeMapper.toTranslation(aboutMe, locale, bio)
                        )
                );
    }
}
