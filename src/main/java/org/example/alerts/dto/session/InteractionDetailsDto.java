package org.example.alerts.dto.session;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.alerts.dto.request.AlertRequest;
import org.example.alerts.dto.response.AlertResponse;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InteractionDetailsDto {

    private String interactionId;
    private AlertRequest request;
    private AlertResponse response;
    private LocalDateTime timestamp;
    private Long timestampMillis;
}