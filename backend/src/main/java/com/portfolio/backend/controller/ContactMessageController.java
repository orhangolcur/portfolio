package com.portfolio.backend.controller;

import com.portfolio.backend.dto.contact.ContactMessageRequest;
import com.portfolio.backend.dto.contact.ContactMessageResponse;
import com.portfolio.backend.service.ContactMessageService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ContactMessageController {

    private final ContactMessageService contactMessageService;

    public ContactMessageController(ContactMessageService contactMessageService) {
        this.contactMessageService = contactMessageService;
    }

    // Herkese açık — ziyaretçi mesaj gönderir
    @PostMapping("/contact")
    public ResponseEntity<Void> send(@Valid @RequestBody ContactMessageRequest request) {
        contactMessageService.send(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // Admin — tüm mesajlar
    @GetMapping("/admin/contact-messages")
    public ResponseEntity<List<ContactMessageResponse>> getAll() {
        return ResponseEntity.ok(contactMessageService.getAll());
    }

    // Admin — okunmamış mesajlar
    @GetMapping("/admin/contact-messages/unread")
    public ResponseEntity<List<ContactMessageResponse>> getUnread() {
        return ResponseEntity.ok(contactMessageService.getUnread());
    }

    // Admin — okundu işaretle
    @PutMapping("/admin/contact-messages/{id}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable UUID id) {
        contactMessageService.markAsRead(id);
        return ResponseEntity.noContent().build();
    }

    // Admin — sil
    @DeleteMapping("/admin/contact-messages/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        contactMessageService.delete(id);
        return ResponseEntity.noContent().build();
    }
}