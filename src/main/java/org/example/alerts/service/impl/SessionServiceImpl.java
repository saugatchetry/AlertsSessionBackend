package org.example.alerts.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.alerts.constant.SessionStatus;
import org.example.alerts.dto.request.AlertRequest;
import org.example.alerts.dto.response.AlertResponse;
import org.example.alerts.dto.session.InteractionDetailsDto;
import org.example.alerts.dto.session.SessionDetailsResponse;
import org.example.alerts.exception.SessionNotFoundException;
import org.example.alerts.model.Session;
import org.example.alerts.model.SessionInteraction;
import org.example.alerts.repository.SessionRepository;
import org.example.alerts.service.SessionPersistenceService;
import org.example.alerts.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;

    @Autowired(required = false)
    private SessionPersistenceService sessionPersistenceService;

    @Override
    public Session createSession() {
        LocalDateTime now = LocalDateTime.now();
        Session session = Session.builder()
                .sessionId(UUID.randomUUID().toString())
                .status(SessionStatus.ACTIVE)
                .createdAt(now)
                .lastAccessedAt(now)
                .build();

        Session savedSession = sessionRepository.save(session);

        // Persist to Cosmos DB asynchronously (if enabled)
        if (sessionPersistenceService != null) {
            sessionPersistenceService.persistSession(savedSession);
        }

        return savedSession;
    }

    @Override
    public Session getSession(String sessionId) {
        // First, try to get from in-memory repository
        return sessionRepository.findById(sessionId)
                .or(() -> {
                    // If not found in memory and Cosmos DB is enabled, try to retrieve from Cosmos DB
                    if (sessionPersistenceService != null) {
                        Session cosmosSession = sessionPersistenceService.retrieveSession(sessionId);
                        if (cosmosSession != null) {
                            // Load the session from Cosmos DB into in-memory repository
                            sessionRepository.save(cosmosSession);
                            return java.util.Optional.of(cosmosSession);
                        }
                    }
                    return java.util.Optional.empty();
                })
                .orElseThrow(() -> new SessionNotFoundException(sessionId));
    }

    @Override
    public SessionDetailsResponse getSessionDetails(String sessionId) {
        Session session = getSession(sessionId);

        List<InteractionDetailsDto> interactionDtos = session.getInteractions().stream()
                .map(this::convertToInteractionDto)
                .collect(Collectors.toList());

        return SessionDetailsResponse.builder()
                .sessionId(session.getSessionId())
                .status(session.getStatus())
                .createdAt(session.getCreatedAt())
                .lastAccessedAt(session.getLastAccessedAt())
                .totalInteractions(session.getInteractions().size())
                .interactions(interactionDtos)
                .build();
    }

    @Override
    public void recordInteraction(String sessionId, AlertRequest request, AlertResponse response) {
        Session session = getSession(sessionId);

        LocalDateTime now = LocalDateTime.now();
        SessionInteraction interaction = SessionInteraction.builder()
                .interactionId(UUID.randomUUID().toString())
                .request(request)
                .response(response)
                .timestamp(now)
                .timestampMillis(now.atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli())
                .build();

        session.addInteraction(interaction);
        Session savedSession = sessionRepository.save(session);

        // Persist to Cosmos DB asynchronously (if enabled)
        if (sessionPersistenceService != null) {
            sessionPersistenceService.persistSession(savedSession);
        }
    }

    @Override
    public void completeSession(String sessionId) {
        Session session = getSession(sessionId);
        session.markCompleted();
        Session savedSession = sessionRepository.save(session);

        // Persist to Cosmos DB asynchronously (if enabled)
        if (sessionPersistenceService != null) {
            sessionPersistenceService.persistSession(savedSession);
        }
    }

    private InteractionDetailsDto convertToInteractionDto(SessionInteraction interaction) {
        return InteractionDetailsDto.builder()
                .interactionId(interaction.getInteractionId())
                .request(interaction.getRequest())
                .response(interaction.getResponse())
                .timestamp(interaction.getTimestamp())
                .timestampMillis(interaction.getTimestampMillis())
                .build();
    }
}