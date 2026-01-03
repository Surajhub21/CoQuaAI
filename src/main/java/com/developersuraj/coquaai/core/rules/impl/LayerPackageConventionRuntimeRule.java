package com.developersuraj.coquaai.core.rules.impl;

import com.developersuraj.coquaai.Entity.ComponentInfo;
import com.developersuraj.coquaai.Entity.Severity;
import com.developersuraj.coquaai.Entity.SourceType;
import com.developersuraj.coquaai.Entity.ViolationReport;
import com.developersuraj.coquaai.core.rules.RuntimeRule;

import java.util.ArrayList;
import java.util.List;

public class LayerPackageConventionRuntimeRule implements RuntimeRule {

    @Override
    public String name() {
        return "Layer Package Convention Rule";
    }

    @Override
    public Severity severity() {
        return Severity.MEDIUM;
    }

    @Override
    public List<ViolationReport> evaluate(List<ComponentInfo> components) {

        List<ViolationReport> violationRuntimes = new ArrayList<>();

        for (ComponentInfo component : components) {

            String packageName = component.getTargetClass().getPackageName();

            switch (component.getType()) {

                case CONTROLLER -> {
                    if (!packageName.contains(".controller")) {
                        violationRuntimes.add(
                                new ViolationReport(
                                        name(),
                                        severity(),
                                        String.format(
                                                "Controller '%s' should be inside a '.controller' package",
                                                component.getName()
                                        ),
                                        SourceType.RUNTIME,
                                        component.getName(),
                                        null
                                )
                        );
                    }
                }

                case SERVICE -> {
                    if (!packageName.contains(".service")) {
                        violationRuntimes.add(
                                new ViolationReport(
                                        name(),
                                        severity(),
                                        String.format(
                                                "Service '%s' should be inside a '.service' package",
                                                component.getName()
                                        ),
                                        SourceType.RUNTIME,
                                        component.getName(),
                                        null
                                )
                        );
                    }
                }

                case REPOSITORY -> {
                    if (!packageName.contains(".repository")) {
                        violationRuntimes.add(
                                new ViolationReport(
                                        name(),
                                        severity(),
                                        String.format(
                                                "Repository '%s' should be inside a '.repository' package",
                                                component.getName()
                                        ),
                                        SourceType.RUNTIME,
                                        component.getName(),
                                        null
                                )
                        );
                    }
                }

                default -> {
                    // ignore other component types
                }
            }
        }

        return violationRuntimes;
    }
}