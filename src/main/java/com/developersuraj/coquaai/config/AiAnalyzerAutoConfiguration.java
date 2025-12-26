package com.developersuraj.coquaai.config;

import com.developersuraj.coquaai.core.analyzer.SpringContextScanner;
import com.developersuraj.coquaai.core.roles.impl.ControllerRepositoryRule;
import com.developersuraj.coquaai.core.engine.RuleEngine;
import com.developersuraj.coquaai.core.roles.impl.NoFieldInjectionRule;
import com.developersuraj.coquaai.web.AiReviewController;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AiAnalyzerAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public SpringContextScanner springContextScanner(
            ApplicationContext context
    ) {
        return new SpringContextScanner(context);
    }

    @Bean
    @ConditionalOnMissingBean
    public RuleEngine ruleEngine() {
        return new RuleEngine(
                List.of(new ControllerRepositoryRule() , new NoFieldInjectionRule())
        );
    }

    @Bean
    @ConditionalOnMissingBean
    public AiReviewController aiReviewController(
            SpringContextScanner scanner,
            RuleEngine ruleEngine
    ) {
        return new AiReviewController(scanner, ruleEngine);
    }
}
