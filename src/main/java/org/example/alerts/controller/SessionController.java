package org.example.alerts.controller;

import lombok.RequiredArgsConstructor;
import org.example.alerts.dto.session.SessionDetailsResponse;
import org.example.alerts.service.SessionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sessions")
@RequiredArgsConstructor
public class SessionController {

    private final SessionService sessionService;

    @GetMapping("/{sessionId}")
    public ResponseEntity<SessionDetailsResponse> getSessionDetails(@PathVariable String sessionId) {
        SessionDetailsResponse sessionDetails = sessionService.getSessionDetails(sessionId);
        return ResponseEntity.status(HttpStatus.OK).body(sessionDetails);
    }
}