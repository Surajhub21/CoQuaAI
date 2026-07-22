package com.developersuraj.coquaai.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "coquaai")
public class CoQuaAIProperties {

    private boolean enabled = true;

    private String basePackage;

    private List<String> excludePackages = new ArrayList<>();

    private int maxPublicMethods = 15;

    private int maxDependencies = 8;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public List<String> getExcludePackages() {
        return excludePackages;
    }

    public void setExcludePackages(List<String> excludePackages) {
        this.excludePackages = excludePackages;
    }

    public int getMaxPublicMethods() {
        return maxPublicMethods;
    }

    public void setMaxPublicMethods(int maxPublicMethods) {
        this.maxPublicMethods = maxPublicMethods;
    }

    public int getMaxDependencies() {
        return maxDependencies;
    }

    public void setMaxDependencies(int maxDependencies) {
        this.maxDependencies = maxDependencies;
    }
}
