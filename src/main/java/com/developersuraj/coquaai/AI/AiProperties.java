package com.developersuraj.coquaai.AI;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "coquaai.ai")
public class AiProperties {

    /**
     * Enable / Disable AI feature.
     */
    private boolean enabled = false;

    /**
     * Gemini API Key
     */
    private String apiKey;

    /**
     * Gemini Model
     */
    private String model = "gemini-2.5-flash";

    /**
     * Gemini Endpoint
     */
    private String endpoint =
            "https://generativelanguage.googleapis.com/v1beta/models";

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }
}