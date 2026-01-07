package org.example.alerts.dto.session;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.alerts.constant.SessionStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionDetailsResponse {

    private String sessionId;
    private SessionStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime lastAccessedAt;
    private Integer totalInteractions;
    private List<InteractionDetailsDto> interactions;
}