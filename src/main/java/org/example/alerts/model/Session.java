package org.example.alerts.model;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.alerts.constant.SessionStatus;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Container(containerName = "alertsInteractionSessions")
public class Session {

    @Id
    private String id;

    @PartitionKey
    private String sessionId;

    @Builder.Default
    private List<SessionInteraction> interactions = new ArrayList<>();

    @Builder.Default
    private SessionStatus status = SessionStatus.ACTIVE;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime lastAccessedAt;

    public void addInteraction(SessionInteraction interaction) {
        this.interactions.add(interaction);
        this.lastAccessedAt = LocalDateTime.now();
    }

    public void markCompleted() {
        this.status = SessionStatus.COMPLETED;
        this.lastAccessedAt = LocalDateTime.now();
    }
}