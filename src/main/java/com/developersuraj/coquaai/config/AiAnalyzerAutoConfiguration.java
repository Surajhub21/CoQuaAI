package com.developersuraj.coquaai.config;

import com.developersuraj.coquaai.core.analyzer.SpringContextScanner;
import com.developersuraj.coquaai.core.engine.RuntimeRuleEngine;
import com.developersuraj.coquaai.core.engine.StaticRuleEngine;
import com.developersuraj.coquaai.core.rules.RuntimeRule;
import com.developersuraj.coquaai.core.rules.impl.*;
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
    public RuntimeRuleEngine runtimeRuleEngine() {
        return new RuntimeRuleEngine(
                List.of(
                        (RuntimeRule) new LayerViolation() ,
                        new NoFieldInjectionRuntimeRule(),
                        new ControllerMultipleResponsibilities(),
                        new NamingConventionViolations(),
                        new TooManyPublicMethods(),
                        new LayerPackageConventionRuntimeRule()
                )
        );
    }

    @Bean
    @ConditionalOnMissingBean
    public StaticRuleEngine staticRuleEngine() {
        return new StaticRuleEngine(
                List.of(new FieldInjectionRuntimeRule())
        );
    }

    @Bean
    @ConditionalOnMissingBean
    public AiReviewController aiReviewController(
            SpringContextScanner scanner,
            RuntimeRuleEngine runtimeRuleEngine,
            StaticRuleEngine staticRuleEngine
    ) {
        return new AiReviewController(scanner, runtimeRuleEngine, staticRuleEngine);
    }
}
