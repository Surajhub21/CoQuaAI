package com.developersuraj.coquaai.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StarterBanner {

    private static final Logger log =
            LoggerFactory.getLogger(StarterBanner.class);

    @Value("${server.port:8080}")
    private String port;

    @EventListener(ApplicationReadyEvent.class)
    public void onReady() {

        log.info("CoQuaAI Starter Loaded");
        log.info("AI Reviews: http://localhost:{}/ai-review", port);
    }

}
