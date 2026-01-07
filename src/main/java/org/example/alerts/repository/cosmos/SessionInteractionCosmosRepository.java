package org.example.alerts.repository.cosmos;

import com.azure.spring.data.cosmos.repository.CosmosRepository;
import org.example.alerts.model.Session;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionInteractionCosmosRepository extends CosmosRepository<Session, String> {

    /**
     * Find session by sessionId (partition key).
     *
     * @param sessionId the session ID
     * @return Optional of Session
     */
    Optional<Session> findBySessionId(String sessionId);
}