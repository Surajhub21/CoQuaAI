package com.developersuraj.coquaai.core.rules.impl;

import com.developersuraj.coquaai.Entity.*;
import com.developersuraj.coquaai.core.rules.RuntimeRule;

import java.util.ArrayList;
import java.util.List;

public class TooManyPublicMethods implements RuntimeRule {
    @Override
    public String name() {
        return "Too Many Public Methods";
    }

    @Override
    public Severity severity() {
        return Severity.LOW;
    }

    @Override
    public List<ViolationReport> evaluate(List<ComponentInfo> components) {
        List<ViolationReport> violationRuntimes = new ArrayList<>();

        for (ComponentInfo component : components) {

            if(component.getType() ==  ComponentType.SERVICE && component.getTargetClass().getMethods().length > 10) {
                violationRuntimes.add(
                        new ViolationReport(
                                name() ,
                                severity() ,
                                String.format(
                                        "Class '%s' has too many public methods are hard to maintain"
                                        ,component.getName()
                                ),
                                SourceType.RUNTIME,
                                component.getName(),
                                null
                        )
                );
            }
        }

        return violationRuntimes;
    }
}
