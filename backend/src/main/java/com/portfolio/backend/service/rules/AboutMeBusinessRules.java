package com.portfolio.backend.service.rules;

import com.portfolio.backend.entity.AboutMe;
import com.portfolio.backend.repository.AboutMeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AboutMeBusinessRules {

    private final AboutMeRepository aboutMeRepository;

    // AboutMe kaydı yoksa hata fırlat
    public AboutMe getAboutMeOrThrow() {
        return aboutMeRepository.findWithTranslations()
                .orElseThrow(() -> new RuntimeException("About me not found"));
    }

    // AboutMe kaydı yoksa yeni oluştur
    public AboutMe getOrCreate() {
        return aboutMeRepository.findWithTranslations()
                .orElseGet(() -> aboutMeRepository.save(AboutMe.builder().build()));
    }
}