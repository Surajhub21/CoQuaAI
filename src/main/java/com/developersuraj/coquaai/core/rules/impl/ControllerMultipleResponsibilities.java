package com.developersuraj.coquaai.core.rules.impl;

import com.developersuraj.coquaai.Entity.ComponentInfo;
import com.developersuraj.coquaai.Entity.ComponentType;
import com.developersuraj.coquaai.Entity.Severity;
import com.developersuraj.coquaai.Entity.Violation;
import com.developersuraj.coquaai.core.rules.Rule;

import java.util.ArrayList;
import java.util.List;

public class ControllerMultipleResponsibilities implements Rule {
    @Override
    public String name() {
        return "Multiple Responsibilities in Controller";
    }

    @Override
    public Severity severity() {
        return Severity.LOW;
    }

    @Override
    public List<Violation> evaluate(List<ComponentInfo> components) {
        List<Violation> violations = new ArrayList<>();

        for(ComponentInfo component : components){

            if(component.getType() == ComponentType.CONTROLLER && component.getDependencies().size() > 4){

                violations.add(
                        new Violation(
                                name(),
                                severity(),
                                String.format(
                                        "Controller '%s' Violates Single Responsibility Principle by injecting so many dependencies (e.g. 4)" ,
                                        component.getName()
                                )
                        )
                );
            }
        }

        return violations;
    }
}
