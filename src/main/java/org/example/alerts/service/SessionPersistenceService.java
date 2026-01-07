package org.example.alerts.service;

import org.example.alerts.model.Session;

public interface SessionPersistenceService {

    /**
     * Persists session to Cosmos DB.
     *
     * @param session the session to persist
     */
    void persistSession(Session session);

    /**
     * Retrieves session from Cosmos DB by session ID.
     *
     * @param sessionId the session ID
     * @return Session if found, null otherwise
     */
    Session retrieveSession(String sessionId);
}