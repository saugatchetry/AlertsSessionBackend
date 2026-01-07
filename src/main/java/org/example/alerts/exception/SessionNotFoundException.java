package org.example.alerts.exception;

public class SessionNotFoundException extends RuntimeException {

    public SessionNotFoundException(String sessionId) {
        super("Session not found with ID: " + sessionId);
    }

    public SessionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}