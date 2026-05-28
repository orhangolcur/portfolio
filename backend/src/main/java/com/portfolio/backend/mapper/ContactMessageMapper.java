package com.portfolio.backend.mapper;

import com.portfolio.backend.dto.contact.ContactMessageRequest;
import com.portfolio.backend.dto.contact.ContactMessageResponse;
import com.portfolio.backend.entity.ContactMessage;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class ContactMessageMapper {

    public ContactMessageResponse toResponse(ContactMessage contactMessage) {
        return ContactMessageResponse.builder()
                .id(contactMessage.getId())
                .senderName(contactMessage.getSenderName())
                .senderEmail(contactMessage.getSenderEmail())
                .message(contactMessage.getMessage())
                .read(contactMessage.isRead())
                .createdAt(contactMessage.getCreatedAt())
                .build();
    }

    public List<ContactMessageResponse> toResponseList(List<ContactMessage> contactMessages) {
        return contactMessages.stream()
                .map(this::toResponse)
                .toList();
    }

    public ContactMessage toEntity(ContactMessageRequest request) {
        return ContactMessage.builder()
                .senderName(request.getSenderName())
                .senderEmail(request.getSenderEmail())
                .message(request.getMessage())
                .build();
    }
}