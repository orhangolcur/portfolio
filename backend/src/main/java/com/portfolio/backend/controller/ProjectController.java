package com.portfolio.backend.controller;

import com.portfolio.backend.dto.project.ProjectRequest;
import com.portfolio.backend.dto.project.ProjectResponse;
import com.portfolio.backend.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/projects")
    public ResponseEntity<List<ProjectResponse>> getAll(
            @RequestParam(defaultValue = "tr") String locale) {
        return ResponseEntity.ok(projectService.getAll(locale));
    }

    @GetMapping("/projects/featured")
    public ResponseEntity<List<ProjectResponse>> getFeatured(
            @RequestParam(defaultValue = "tr") String locale) {
        return ResponseEntity.ok(projectService.getFeatured(locale));
    }

    @GetMapping("/projects/{id}")
    public ResponseEntity<ProjectResponse> getById(
            @PathVariable UUID id,
            @RequestParam(defaultValue = "tr") String locale) {
        return ResponseEntity.ok(projectService.getById(id, locale));
    }

    @PostMapping("/admin/projects")
    public ResponseEntity<ProjectResponse> create(
            @Valid @RequestBody ProjectRequest request,
            @RequestParam(defaultValue = "tr") String locale) {
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.create(request, locale));
    }

    @PutMapping("/admin/projects/{id}")
    public ResponseEntity<ProjectResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody ProjectRequest request,
            @RequestParam(defaultValue = "tr") String locale) {
        return ResponseEntity.ok(projectService.update(id, request, locale));
    }

    @DeleteMapping("/admin/projects/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        projectService.delete(id);
        return ResponseEntity.noContent().build();
    }
}