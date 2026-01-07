package org.example.alerts.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlertResponse {

    private String requestId;
    private String sessionId;
    private Integer status;
    private String message;
    private List<Alert> alerts;
}