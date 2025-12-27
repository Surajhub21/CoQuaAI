package com.developersuraj.coquaai.core.rules.impl;

import com.developersuraj.coquaai.Entity.ComponentInfo;
import com.developersuraj.coquaai.Entity.ComponentType;
import com.developersuraj.coquaai.Entity.Severity;
import com.developersuraj.coquaai.Entity.Violation;
import com.developersuraj.coquaai.core.rules.Rule;

import java.util.ArrayList;
import java.util.List;

public class TooManyPublicMethods implements Rule {
    @Override
    public String name() {
        return "Too Many Public Methods";
    }

    @Override
    public Severity severity() {
        return Severity.LOW;
    }

    @Override
    public List<Violation> evaluate(List<ComponentInfo> components) {
        List<Violation> violations = new ArrayList<>();

        for (ComponentInfo component : components) {

            if(component.getType() ==  ComponentType.SERVICE && component.getTargetClass().getMethods().length > 10) {
                violations.add(
                        new Violation(
                                name() ,
                                severity() ,
                                String.format(
                                        "Class '%s' has too many public methods are hard to maintain"
                                        ,component.getName()
                                )
                        )
                );
            }
        }

        return violations;
    }
}
