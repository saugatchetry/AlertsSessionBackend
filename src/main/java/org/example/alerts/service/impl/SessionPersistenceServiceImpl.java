package org.example.alerts.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.alerts.model.Session;
import org.example.alerts.repository.cosmos.SessionInteractionCosmosRepository;
import org.example.alerts.service.SessionPersistenceService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "azure.cosmos.enabled", havingValue = "true", matchIfMissing = false)
@RequiredArgsConstructor
@Slf4j
public class SessionPersistenceServiceImpl implements SessionPersistenceService {

    private final SessionInteractionCosmosRepository cosmosRepository;

    @Override
    public void persistSession(Session session) {
        try {
            // Set id same as sessionId for Cosmos DB document identification
            session.setId(session.getSessionId());
            cosmosRepository.save(session);
            log.debug("Successfully persisted session to Cosmos DB. SessionId: {}", session.getSessionId());
        } catch (Exception e) {
            // Log error but don't fail the request - Cosmos DB persistence is supplementary
            log.error("Failed to persist session to Cosmos DB. SessionId: {}", session.getSessionId(), e);
        }
    }

    @Override
    public Session retrieveSession(String sessionId) {
        try {
            return cosmosRepository.findBySessionId(sessionId).orElse(null);
        } catch (Exception e) {
            log.error("Failed to retrieve session from Cosmos DB. SessionId: {}", sessionId, e);
            return null;
        }
    }
}