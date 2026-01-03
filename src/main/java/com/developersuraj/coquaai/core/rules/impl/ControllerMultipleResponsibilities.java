package com.developersuraj.coquaai.core.rules.impl;

import com.developersuraj.coquaai.Entity.*;
import com.developersuraj.coquaai.core.rules.RuntimeRule;

import java.util.ArrayList;
import java.util.List;

public class ControllerMultipleResponsibilities implements RuntimeRule {
    @Override
    public String name() {
        return "Multiple Responsibilities in Controller";
    }

    @Override
    public Severity severity() {
        return Severity.LOW;
    }

    @Override
    public List<ViolationReport> evaluate(List<ComponentInfo> components) {
        List<ViolationReport> violationRuntimes = new ArrayList<>();

        for(ComponentInfo component : components){

            if(component.getType() == ComponentType.CONTROLLER && component.getDependencies().size() > 4){

                violationRuntimes.add(
                        new ViolationReport(
                                name(),
                                severity(),
                                String.format(
                                        "Controller '%s' Violates Single Responsibility Principle by injecting so many dependencies (e.g. 4)" ,
                                        component.getName()
                                ),
                                SourceType.RUNTIME,
                                component.getName()+".java",
                                null
                        )
                );
            }
        }

        return violationRuntimes;
    }
}
