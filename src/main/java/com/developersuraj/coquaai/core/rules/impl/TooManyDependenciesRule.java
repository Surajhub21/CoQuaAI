package com.developersuraj.coquaai.core.rules.impl;

import com.developersuraj.coquaai.Entity.*;
import com.developersuraj.coquaai.core.rules.RuntimeRule;

import java.util.ArrayList;
import java.util.List;

public class TooManyDependenciesRule implements RuntimeRule {

    private final int maxDependencies;

    public TooManyDependenciesRule(int maxDependencies) {
        this.maxDependencies = maxDependencies;
    }

    @Override
    public String name() {
        return "Too Many Dependencies";
    }

    @Override
    public Severity severity() {
        return Severity.MEDIUM;
    }

    @Override
    public List<ViolationReport> evaluate(List<ComponentInfo> components) {

        List<ViolationReport> violationRuntimes = new ArrayList<>();

        for (ComponentInfo component : components) {

            if (component.getDependencies().size() > maxDependencies) {

                violationRuntimes.add(
                        new ViolationReport(
                                name(),
                                severity(),
                                String.format(
                                        "Class '%s' injects %d dependencies, exceeding the recommended maximum of %d. Consider splitting its responsibilities.",
                                        component.getName(),
                                        component.getDependencies().size(),
                                        maxDependencies
                                ),
                                SourceType.RUNTIME,
                                component.getName() + ".java",
                                null
                        )
                );
            }
        }

        return violationRuntimes;
    }
}
