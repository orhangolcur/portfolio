package com.portfolio.backend.controller;

import com.portfolio.backend.dto.technology.TechnologyRequest;
import com.portfolio.backend.dto.technology.TechnologyResponse;
import com.portfolio.backend.service.TechnologyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class TechnologyController {

    private final TechnologyService technologyService;

    public TechnologyController(TechnologyService technologyService) {
        this.technologyService = technologyService;
    }

    @GetMapping("/technologies")
    public ResponseEntity<List<TechnologyResponse>> getAll() {
        return ResponseEntity.ok(technologyService.getAll());
    }

    @GetMapping("/technologies/{id}")
    public ResponseEntity<TechnologyResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(technologyService.getById(id));
    }

    @PostMapping("/admin/technologies")
    public ResponseEntity<TechnologyResponse> create(@Valid @RequestBody TechnologyRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(technologyService.create(request));
    }

    @PutMapping("/admin/technologies/{id}")
    public ResponseEntity<TechnologyResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody TechnologyRequest request) {
        return ResponseEntity.ok(technologyService.update(id, request));
    }

    @DeleteMapping("/admin/technologies/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        technologyService.delete(id);
        return ResponseEntity.noContent().build();
    }
}