package com.portfolio.backend.service;

import com.portfolio.backend.dto.contact.ContactMessageRequest;
import com.portfolio.backend.dto.contact.ContactMessageResponse;
import com.portfolio.backend.entity.ContactMessage;
import com.portfolio.backend.mapper.ContactMessageMapper;
import com.portfolio.backend.repository.ContactMessageRepository;
import com.portfolio.backend.service.rules.ContactMessageBusinessRules;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class ContactMessageService {

    private final ContactMessageRepository contactMessageRepository;
    private final ContactMessageBusinessRules contactMessageBusinessRules;
    private final ContactMessageMapper contactMessageMapper;

    public ContactMessageService(
            ContactMessageRepository contactMessageRepository,
            ContactMessageBusinessRules contactMessageBusinessRules,
            ContactMessageMapper contactMessageMapper) {
        this.contactMessageRepository = contactMessageRepository;
        this.contactMessageBusinessRules = contactMessageBusinessRules;
        this.contactMessageMapper = contactMessageMapper;
    }

    @Transactional(readOnly = true)
    public List<ContactMessageResponse> getAll() {
        List<ContactMessage> messages = contactMessageBusinessRules.getAll();
        return contactMessageMapper.toResponseList(messages);
    }

    @Transactional(readOnly = true)
    public List<ContactMessageResponse> getUnread() {
        List<ContactMessage> messages = contactMessageBusinessRules.getUnread();
        return contactMessageMapper.toResponseList(messages);
    }

    @Transactional
    public void send(ContactMessageRequest request) {
        ContactMessage message = contactMessageMapper.toEntity(request);
        contactMessageRepository.save(message);
    }

    @Transactional
    public void markAsRead(UUID id) {
        ContactMessage message = contactMessageBusinessRules.getByIdOrThrow(id);
        message.setRead(true);
        contactMessageRepository.save(message);
    }

    @Transactional
    public void delete(UUID id) {
        contactMessageBusinessRules.checkExistsOrThrow(id);
        contactMessageRepository.deleteById(id);
    }
}