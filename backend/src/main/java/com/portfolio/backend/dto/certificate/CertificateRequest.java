package com.portfolio.backend.dto.certificate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CertificateRequest {

    @NotBlank(message = "Turkish title is required.")
    @Size(max = 255, message = "Turkish title must be less than 255 characters.")
    private String titleTr;

    @NotBlank(message = "English title is required.")
    @Size(max = 255, message = "English title must be less than 255 characters.")
    private String titleEn;

    @NotBlank(message = "Issuer is required.")
    @Size(max = 255, message = "Issuer must be less than 255 characters.")
    private String issuer;

    @URL(message = "Credential URL must be a valid URL.")
    @Size(min = 1, max = 500, message = "Credential URL must be less than 500 characters.")
    private String credentialUrl;

    @URL(message = "Image URL must be a valid URL.")
    @Size(min = 1, max = 500, message = "Image URL must be less than 500 characters.")
    private String imageUrl;

    @PastOrPresent(message = "Issued date cannot be in the future.")
    private LocalDate issuedDate;
}