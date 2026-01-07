package org.example.alerts.service;

import org.example.alerts.dto.request.AlertRequest;
import org.example.alerts.dto.response.AlertResponse;

public interface AlertService {

    /**
     * Processes an alert request and generates a response with alerts.
     *
     * @param request the alert request containing language, anonymous flag, and text
     * @param sessionId the session ID for tracking user interactions
     * @return AlertResponse containing request ID, session ID, status, message, and list of alerts
     */
    AlertResponse processAlert(AlertRequest request, String sessionId);
}