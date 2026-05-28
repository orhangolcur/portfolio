package com.portfolio.backend.controller;

import com.portfolio.backend.dto.certificate.CertificateRequest;
import com.portfolio.backend.dto.certificate.CertificateResponse;
import com.portfolio.backend.service.CertificateService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class CertificateController {

    private final CertificateService certificateService;

    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @GetMapping("/certificates")
    public ResponseEntity<List<CertificateResponse>> getAll() {
        return ResponseEntity.ok(certificateService.getAll());
    }

    @GetMapping("/certificates/{id}")
    public ResponseEntity<CertificateResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(certificateService.getById(id));
    }

    @PostMapping("/admin/certificates")
    public ResponseEntity<CertificateResponse> create(@Valid @RequestBody CertificateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(certificateService.create(request));
    }

    @PutMapping("/admin/certificates/{id}")
    public ResponseEntity<CertificateResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody CertificateRequest request) {
        return ResponseEntity.ok(certificateService.update(id, request));
    }

    @DeleteMapping("/admin/certificates/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        certificateService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
