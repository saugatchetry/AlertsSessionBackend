package org.example.alerts.repository;

import org.example.alerts.model.Session;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class SessionRepository {

    private final Map<String, Session> sessionStore = new ConcurrentHashMap<>();

    public Session save(Session session) {
        sessionStore.put(session.getSessionId(), session);
        return session;
    }

    public Optional<Session> findById(String sessionId) {
        return Optional.ofNullable(sessionStore.get(sessionId));
    }

    public boolean exists(String sessionId) {
        return sessionStore.containsKey(sessionId);
    }

    public void deleteById(String sessionId) {
        sessionStore.remove(sessionId);
    }

    public void clear() {
        sessionStore.clear();
    }

    public long count() {
        return sessionStore.size();
    }
}