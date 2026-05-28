package com.portfolio.backend.service.rules;

import com.portfolio.backend.entity.ContactMessage;
import com.portfolio.backend.exception.ResourceNotFoundException;
import com.portfolio.backend.repository.ContactMessageRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ContactMessageBusinessRules {

    private final ContactMessageRepository contactMessageRepository;

    public ContactMessageBusinessRules(ContactMessageRepository contactMessageRepository) {
        this.contactMessageRepository = contactMessageRepository;
    }

    public ContactMessage getByIdOrThrow(UUID id) {
        return contactMessageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact message not found: " + id));
    }

    public List<ContactMessage> getAll() {
        return contactMessageRepository.findAllByOrderByCreatedAtDesc();
    }

    public List<ContactMessage> getUnread() {
        return contactMessageRepository.findByIsReadFalseOrderByCreatedAtDesc();
    }

    public void checkExistsOrThrow(UUID id) {
        if (!contactMessageRepository.existsById(id)) {
            throw new ResourceNotFoundException("Contact message not found: " + id);
        }
    }
}