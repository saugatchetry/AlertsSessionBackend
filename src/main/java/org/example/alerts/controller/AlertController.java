package org.example.alerts.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.alerts.constant.HeaderConstants;
import org.example.alerts.dto.request.AlertRequest;
import org.example.alerts.dto.response.AlertResponse;
import org.example.alerts.exception.SessionNotFoundException;
import org.example.alerts.model.Session;
import org.example.alerts.service.AlertService;
import org.example.alerts.service.SessionService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AlertController {

    private final AlertService alertService;
    private final SessionService sessionService;

    @PostMapping("/alerts")
    public ResponseEntity<AlertResponse> createAlert(
            @Valid @RequestBody AlertRequest request,
            @RequestHeader(value = HeaderConstants.SESSION_ID_HEADER, required = false) String sessionId) {

        // Create new session if not provided, otherwise validate existing session
        String activeSessionId = determineSessionId(sessionId);

        // Process alert and get response
        AlertResponse response = alertService.processAlert(request, activeSessionId);

        // Record interaction in session
        sessionService.recordInteraction(activeSessionId, request, response);

        // Add session ID to response headers
        HttpHeaders headers = new HttpHeaders();
        headers.add(HeaderConstants.SESSION_ID_HEADER, activeSessionId);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(response);
    }

    private String determineSessionId(String sessionId) {
        if (StringUtils.hasText(sessionId)) {
            try {
                // Try to get existing session from memory or DB
                sessionService.getSession(sessionId);
                return sessionId;
            } catch (SessionNotFoundException e) {
                // Session not found in memory or DB, create a new session
                Session newSession = sessionService.createSession();
                return newSession.getSessionId();
            }
        } else {
            // Create new session
            Session newSession = sessionService.createSession();
            return newSession.getSessionId();
        }
    }
}