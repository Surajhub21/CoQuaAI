package com.developersuraj.coquaai.core.roles.impl;

import com.developersuraj.coquaai.Entity.ComponentInfo;
import com.developersuraj.coquaai.Entity.Severity;
import com.developersuraj.coquaai.Entity.Violation;
import com.developersuraj.coquaai.core.roles.Rule;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class NoFieldInjectionRule implements Rule {

    @Override
    public String name() {
        return "Field Injection Detected";
    }

    @Override
    public Severity severity() {
        return Severity.MEDIUM;
    }

    @Override
    public List<Violation> evaluate(List<ComponentInfo> components) {

        List<Violation> violations = new ArrayList<>();

        for (ComponentInfo component : components) {

            Class<?> clazz = component.getTargetClass();

            for (Field field : clazz.getDeclaredFields()) {

                if (field.isAnnotationPresent(Autowired.class)) {

                    violations.add(
                            new Violation(
                                    name(),
                                    severity(),
                                    component.getName()
                                            + " uses field injection: "
                                            + field.getName()
                            )
                    );
                }
            }
        }

        return violations;
    }
}
