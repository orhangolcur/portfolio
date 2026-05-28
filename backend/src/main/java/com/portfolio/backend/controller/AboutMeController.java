package com.portfolio.backend.controller;

import com.portfolio.backend.dto.about.AboutMeRequest;
import com.portfolio.backend.dto.about.AboutMeResponse;
import com.portfolio.backend.service.AboutMeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AboutMeController {

    private final AboutMeService aboutMeService;

    public AboutMeController(AboutMeService aboutMeService) {
        this.aboutMeService = aboutMeService;
    }

    @GetMapping("/about-me")
    public ResponseEntity<AboutMeResponse> getAboutMe() {
        return ResponseEntity.ok(aboutMeService.getAboutMe());
    }

    @PutMapping("/admin/about-me")
    public ResponseEntity<Void> updateAboutMe(@Valid @RequestBody AboutMeRequest request) {
        aboutMeService.updateAboutMe(request);
        return ResponseEntity.noContent().build();
    }
}
