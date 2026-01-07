package org.example.alerts.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.alerts.dto.request.AlertRequest;
import org.example.alerts.dto.response.Alert;
import org.example.alerts.dto.response.AlertResponse;
import org.example.alerts.service.AlertService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AlertServiceImpl implements AlertService {

    private static final Random RANDOM = new Random();
    private static final int MIN_STATUS = 1000;
    private static final int MAX_STATUS = 9999;

    @Override
    public AlertResponse processAlert(AlertRequest request, String sessionId) {
        String requestId = generateRequestId();
        Integer status = generateRandomStatus();
        List<Alert> alerts = buildStandardAlerts();

        return AlertResponse.builder()
                .requestId(requestId)
                .sessionId(sessionId)
                .status(status)
                .message("Alert processed successfully")
                .alerts(alerts)
                .build();
    }

    private String generateRequestId() {
        return UUID.randomUUID().toString();
    }

    private Integer generateRandomStatus() {
        return RANDOM.nextInt(MAX_STATUS - MIN_STATUS + 1) + MIN_STATUS;
    }

    private List<Alert> buildStandardAlerts() {
        return List.of(
                Alert.builder()
                        .message("Where did it happen?")
                        .description("Add the area (e.g., electronics, backroom)")
                        .build(),
                Alert.builder()
                        .message("When did this occur?")
                        .description("Provide a date or shift timings. (e.g., January 1, multiple dates)")
                        .build(),
                Alert.builder()
                        .message("Who was directly involved?")
                        .description("If known, include names and roles (e.g., John Smith (Dept Manager); A. Lopez (Team Lead)).")
                        .build()
        );
    }
}