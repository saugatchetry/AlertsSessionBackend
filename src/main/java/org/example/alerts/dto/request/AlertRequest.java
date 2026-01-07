package org.example.alerts.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlertRequest {

    @NotBlank(message = "Language code is required")
    private String lang;

    @NotBlank(message = "Anonymous flag is required")
    private String anonymous;

    @NotBlank(message = "Text is required")
    private String text;
}