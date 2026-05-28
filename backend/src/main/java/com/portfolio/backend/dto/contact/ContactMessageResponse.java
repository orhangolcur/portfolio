package com.portfolio.backend.dto.contact;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactMessageResponse {
    private UUID id;
    private String senderName;
    private String senderEmail;
    private String message;
    private boolean read;
    private LocalDateTime createdAt;
}