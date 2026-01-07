package org.example.alerts.config;

import com.azure.cosmos.CosmosClientBuilder;
import com.azure.cosmos.GatewayConnectionConfig;
import com.azure.spring.data.cosmos.config.AbstractCosmosConfiguration;
import com.azure.spring.data.cosmos.config.CosmosConfig;
import com.azure.spring.data.cosmos.repository.config.EnableCosmosRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "azure.cosmos.enabled", havingValue = "true", matchIfMissing = false)
@EnableCosmosRepositories(basePackages = "org.example.alerts.repository.cosmos")
public class CosmosDbConfiguration extends AbstractCosmosConfiguration {

    @Value("${azure.cosmos.uri}")
    private String uri;

    @Value("${azure.cosmos.key}")
    private String key;

    @Value("${azure.cosmos.database}")
    private String database;

    @Override
    protected String getDatabaseName() {
        return database;
    }

    @Bean
    public CosmosClientBuilder getCosmosClientBuilder() {
        // Use Gateway mode for local emulator
        GatewayConnectionConfig gatewayConfig = new GatewayConnectionConfig();

        return new CosmosClientBuilder()
                .endpoint(uri)
                .key(key)
                .gatewayMode(gatewayConfig);
    }

    @Bean
    public CosmosConfig getCosmosConfig() {
        return CosmosConfig.builder()
                .enableQueryMetrics(false)
                .responseDiagnosticsProcessor(responseDiagnostics -> {
                    // Can add diagnostics processing here if needed
                })
                .build();
    }
}