package com.portfolio.backend.controller;

import com.portfolio.backend.dto.education.EducationRequest;
import com.portfolio.backend.dto.education.EducationResponse;
import com.portfolio.backend.service.EducationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class EducationController {

    private final EducationService educationService;

    public EducationController(EducationService educationService) {
        this.educationService = educationService;
    }

    @GetMapping("/educations")
    public ResponseEntity<List<EducationResponse>> getAll() {
        return ResponseEntity.ok(educationService.getAll());
    }

    @GetMapping("/educations/{id}")
    public ResponseEntity<EducationResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(educationService.getById(id));
    }

    @PostMapping("/admin/educations")
    public ResponseEntity<EducationResponse> create(@Valid @RequestBody EducationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(educationService.create(request));
    }

    @PutMapping("/admin/educations/{id}")
    public ResponseEntity<EducationResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody EducationRequest request) {
        return ResponseEntity.ok(educationService.update(id, request));
    }

    @DeleteMapping("/admin/educations/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        educationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
