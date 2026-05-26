package com.portfolio.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.portfolio.backend.dto.about.AboutMeRequest;
import com.portfolio.backend.dto.about.AboutMeResponse;
import com.portfolio.backend.service.AboutMeService;

@RestController
@RequestMapping("/api")
public class AboutMeController {

    private final AboutMeService aboutMeService;

    public AboutMeController(AboutMeService aboutMeService) {
        this.aboutMeService = aboutMeService;
    }

    @GetMapping("/about-me")
    public ResponseEntity<AboutMeResponse> getAboutMe(
        @RequestParam(defaultValue = "tr") String locale
    ) {
        return ResponseEntity.ok(aboutMeService.getAboutMe(locale));
    }

    @PutMapping("/admin/about-me")
    public ResponseEntity<Void> updateAboutMe(@RequestBody AboutMeRequest request) {
        aboutMeService.updateAboutMe(request);
        return ResponseEntity.noContent().build();
    }

}
