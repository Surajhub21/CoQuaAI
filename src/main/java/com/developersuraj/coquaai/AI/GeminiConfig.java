package com.developersuraj.coquaai.AI;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class GeminiConfig {

    @Bean
    @ConditionalOnProperty(
            prefix = "coquaai.ai",
            name = "enabled",
            havingValue = "true")
    RestClient geminiRestClient() {

        return RestClient.builder()
                .build();
    }

}
