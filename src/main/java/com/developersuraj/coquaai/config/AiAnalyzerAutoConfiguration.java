package com.developersuraj.coquaai.config;

import com.developersuraj.coquaai.AI.AiProperties;
import com.developersuraj.coquaai.AI.GeminiService;
import com.developersuraj.coquaai.core.analyzer.SpringContextScanner;
import com.developersuraj.coquaai.core.engine.RuntimeRuleEngine;
import com.developersuraj.coquaai.core.engine.StaticRuleEngine;
import com.developersuraj.coquaai.core.rules.RuntimeRule;
import com.developersuraj.coquaai.core.rules.StaticRule;
import com.developersuraj.coquaai.core.rules.impl.*;
import com.developersuraj.coquaai.core.score.ProjectScoreCalculator;
import com.developersuraj.coquaai.web.AIPageController;
import com.developersuraj.coquaai.web.AiReviewController;
import com.developersuraj.coquaai.web.StarterBanner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import java.util.List;

@Configuration
@EnableConfigurationProperties({
        CoQuaAIProperties.class,
        AiProperties.class
})
@ConditionalOnProperty(prefix = "coquaai", name = "enabled", havingValue = "true", matchIfMissing = true)
public class AiAnalyzerAutoConfiguration {

    @Bean
    @ConditionalOnProperty(
            prefix = "coquaai.ai",
            name = "enabled",
            havingValue = "true")
    public GeminiService geminiService(
            RestClient restClient,
            AiProperties properties
    ) {

        return new GeminiService(restClient, properties);

    }

    @Bean
    @ConditionalOnMissingBean
    public SpringContextScanner springContextScanner(
            ApplicationContext context,
            CoQuaAIProperties properties
    ) {
        return new SpringContextScanner(context, properties);
    }

    // ---------------------------------------------------------------
    // Runtime rules - each is its own bean so Spring can collect them
    // into a single List<RuntimeRule> for the engine below.
    // ---------------------------------------------------------------

    @Bean
    public RuntimeRule layerViolationRule() {
        return new LayerViolation();
    }

    @Bean
    public RuntimeRule noFieldInjectionRuntimeRule() {
        return new NoFieldInjectionRuntimeRule();
    }

    @Bean
    public RuntimeRule namingConventionViolationsRule() {
        return new NamingConventionViolations();
    }

    @Bean
    public RuntimeRule layerPackageConventionRuntimeRule() {
        return new LayerPackageConventionRuntimeRule();
    }

    @Bean
    public RuntimeRule tooManyDependenciesRule(CoQuaAIProperties properties) {
        return new TooManyDependenciesRule(properties.getMaxDependencies());
    }

    @Bean
    public RuntimeRule excessivePublicMethodsRule(CoQuaAIProperties properties) {
        return new ExcessivePublicMethodsRule(properties.getMaxPublicMethods());
    }

    @Bean
    @ConditionalOnMissingBean
    public RuntimeRuleEngine runtimeRuleEngine(List<RuntimeRule> runtimeRules) {
        return new RuntimeRuleEngine(runtimeRules);
    }

    // ---------------------------------------------------------------
    // Static rules - same pattern as above, collected via List<StaticRule>.
    // ---------------------------------------------------------------

    @Bean
    public StaticRule fieldInjectionRuntimeRule() {
        return new FieldInjectionRuntimeRule();
    }

    @Bean
    public StaticRule missingTransactionalRule() {
        return new MissingTransactionalRule();
    }

    @Bean
    public StaticRule missingValidatedRequestBodyRule() {
        return new MissingValidatedRequestBodyRule();
    }

    @Bean
    public StaticRule duplicateRequestMappingRule() {
        return new DuplicateRequestMappingRule();
    }

    @Bean
    public StaticRule configurationNamingConventionRule() {
        return new ConfigurationNamingConventionRule();
    }

    @Bean
    public StaticRule dtoLeakageRule() {
        return new DtoLeakageRule();
    }

    @Bean
    public StaticRule entityReturnTypeRule() {
        return new EntityReturnTypeRule();
    }

    @Bean
    public StaticRule missingResponseEntityRule() {
        return new MissingResponseEntityRule();
    }

    @Bean
    public StaticRule missingRequestMappingAnnotationRule() {
        return new MissingRequestMappingAnnotationRule();
    }

    @Bean
    public StaticRule tooManyEndpointsRule() {
        return new TooManyEndpointsRule();
    }

    @Bean
    public StaticRule longMethodRule() {
        return new LongMethodRule();
    }

    @Bean
    public StaticRule emptyCatchBlockRule() {
        return new EmptyCatchBlockRule();
    }

    @Bean
    public StaticRule systemOutUsageRule() {
        return new SystemOutUsageRule();
    }

    @Bean
    public StaticRule todoFixmeCommentRule() {
        return new TodoFixmeCommentRule();
    }

    @Bean
    public StaticRule magicNumberRule() {
        return new MagicNumberRule();
    }

    @Bean
    @ConditionalOnMissingBean
    public StaticRuleEngine staticRuleEngine(List<StaticRule> staticRules) {
        return new StaticRuleEngine(staticRules);
    }

    // ---------------------------------------------------------------

    @Bean
    @ConditionalOnMissingBean
    public ProjectScoreCalculator projectScoreCalculator() {
        return new ProjectScoreCalculator();
    }

    @Bean
    @ConditionalOnMissingBean
    public AiReviewController aiReviewController(
            SpringContextScanner scanner,
            RuntimeRuleEngine runtimeRuleEngine,
            StaticRuleEngine staticRuleEngine,
            ProjectScoreCalculator projectScoreCalculator,
            GeminiService geminiService
    ) {
        return new AiReviewController(scanner, runtimeRuleEngine, staticRuleEngine, projectScoreCalculator, geminiService);
    }

    @Bean
    @ConditionalOnMissingBean
    public StarterBanner starterBanner(){
        return new StarterBanner();
    }

    @Bean
    @ConditionalOnMissingBean
    public AIPageController aiPageController(){
        return new AIPageController();
    }
}
