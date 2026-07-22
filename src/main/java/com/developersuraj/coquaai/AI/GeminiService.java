package com.developersuraj.coquaai.AI;

import com.developersuraj.coquaai.AI.dto.GeminiRequest;
import com.developersuraj.coquaai.AI.dto.GeminiResponse;
import com.developersuraj.coquaai.Entity.ViolationReport;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class GeminiService {

    private final RestClient restClient;
    private final AiProperties properties;

    public GeminiService(
            RestClient restClient,
            AiProperties properties
    ) {
        this.restClient = restClient;
        this.properties = properties;
    }

    public String explain(ViolationReport violation) {

        String prompt =
                GeminiPromptBuilder.buildPrompt(violation);

        GeminiRequest request = new GeminiRequest(

                List.of(

                        new GeminiRequest.Content(

                                List.of(

                                        new GeminiRequest.Part(prompt)

                                )

                        )

                )

        );

        GeminiResponse response = restClient.post()

                .uri(properties.getEndpoint()
                        + "/"
                        + properties.getModel()
                        + ":generateContent?key="
                        + properties.getApiKey())

                .contentType(MediaType.APPLICATION_JSON)

                .body(request)

                .retrieve()

                .body(GeminiResponse.class);

        if (response == null
                || response.candidates() == null
                || response.candidates().isEmpty()) {

            return "No explanation generated.";
        }

        return response.candidates()
                .getFirst()
                .content()
                .parts()
                .getFirst()
                .text();
    }

}