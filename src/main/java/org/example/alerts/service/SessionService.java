package org.example.alerts.service;

import org.example.alerts.dto.request.AlertRequest;
import org.example.alerts.dto.response.AlertResponse;
import org.example.alerts.dto.session.SessionDetailsResponse;
import org.example.alerts.model.Session;

public interface SessionService {

    /**
     * Creates a new session.
     *
     * @return newly created Session
     */
    Session createSession();

    /**
     * Retrieves an existing session by ID.
     *
     * @param sessionId the session ID
     * @return Session if found
     * @throws org.example.alerts.exception.SessionNotFoundException if session not found
     */
    Session getSession(String sessionId);

    /**
     * Retrieves session details with full interaction history.
     *
     * @param sessionId the session ID
     * @return SessionDetailsResponse containing session metadata and interactions
     * @throws org.example.alerts.exception.SessionNotFoundException if session not found
     */
    SessionDetailsResponse getSessionDetails(String sessionId);

    /**
     * Records an interaction (request-response pair) in the session.
     *
     * @param sessionId the session ID
     * @param request the alert request
     * @param response the alert response
     */
    void recordInteraction(String sessionId, AlertRequest request, AlertResponse response);

    /**
     * Marks a session as completed (when alerts list is empty).
     *
     * @param sessionId the session ID
     */
    void completeSession(String sessionId);
}