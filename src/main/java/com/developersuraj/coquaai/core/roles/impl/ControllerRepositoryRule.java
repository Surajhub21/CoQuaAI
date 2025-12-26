package com.developersuraj.coquaai.core.roles.impl;

import com.developersuraj.coquaai.Entity.ComponentInfo;
import com.developersuraj.coquaai.Entity.ComponentType;
import com.developersuraj.coquaai.Entity.Severity;
import com.developersuraj.coquaai.Entity.Violation;
import com.developersuraj.coquaai.core.roles.Rule;

import java.util.ArrayList;
import java.util.List;

public class ControllerRepositoryRule implements Rule {
    @Override
    public String name() {
        return "Controller-Repository Dependency";
    }

    @Override
    public Severity severity() {
        return Severity.HIGH;
    }

    @Override
    public List<Violation> evaluate(List<ComponentInfo> components) {
        List<Violation> violations = new ArrayList<>();

        for (ComponentInfo component : components) {

            if (component.getType() != ComponentType.CONTROLLER) {
                continue;
            }

            for (Class<?> dep : component.getDependencies()) {

                if (org.springframework.data.repository.Repository
                        .class.isAssignableFrom(dep)) {

                    violations.add(
                            new Violation(
                                    name(),
                                    severity(),
                                    component.getName()
                                            + " directly depends on "
                                            + dep.getSimpleName()
                            ));
                }
            }
        }
        return violations;
    }
}
