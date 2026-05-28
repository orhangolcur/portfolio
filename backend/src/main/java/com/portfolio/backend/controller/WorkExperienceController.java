package com.portfolio.backend.controller;

import com.portfolio.backend.dto.workexperience.WorkExperienceRequest;
import com.portfolio.backend.dto.workexperience.WorkExperienceResponse;
import com.portfolio.backend.service.WorkExperienceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class WorkExperienceController {

    private final WorkExperienceService workExperienceService;

    public WorkExperienceController(WorkExperienceService workExperienceService) {
        this.workExperienceService = workExperienceService;
    }

    @GetMapping("/work-experiences")
    public ResponseEntity<List<WorkExperienceResponse>> getAll() {
        return ResponseEntity.ok(workExperienceService.getAll());
    }

    @GetMapping("/work-experiences/{id}")
    public ResponseEntity<WorkExperienceResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(workExperienceService.getById(id));
    }

    @PostMapping("/admin/work-experiences")
    public ResponseEntity<WorkExperienceResponse> create(
            @Valid @RequestBody WorkExperienceRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(workExperienceService.create(request));
    }

    @PutMapping("/admin/work-experiences/{id}")
    public ResponseEntity<WorkExperienceResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody WorkExperienceRequest request) {
        return ResponseEntity.ok(workExperienceService.update(id, request));
    }

    @DeleteMapping("/admin/work-experiences/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        workExperienceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}