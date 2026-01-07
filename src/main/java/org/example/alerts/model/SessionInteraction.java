package org.example.alerts.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.alerts.dto.request.AlertRequest;
import org.example.alerts.dto.response.AlertResponse;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionInteraction {

    private String interactionId;
    private AlertRequest request;
    private AlertResponse response;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime timestamp;

    private Long timestampMillis;

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        if (timestamp != null) {
            this.timestampMillis = timestamp.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        }
    }
}