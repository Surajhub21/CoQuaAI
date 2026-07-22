package com.developersuraj.coquaai.core.rules.impl;

import com.developersuraj.coquaai.Entity.*;
import com.developersuraj.coquaai.core.rules.RuntimeRule;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class ExcessivePublicMethodsRule implements RuntimeRule {

    private static final int MAX_PUBLIC_METHODS = 15;

    @Override
    public String name() {
        return "Excessive Public Methods";
    }

    @Override
    public Severity severity() {
        return Severity.LOW;
    }

    @Override
    public List<ViolationReport> evaluate(List<ComponentInfo> components) {

        List<ViolationReport> violationRuntimes = new ArrayList<>();

        for (ComponentInfo component : components) {

            long publicMethodCount = countDeclaredPublicMethods(component.getTargetClass());

            if (publicMethodCount > MAX_PUBLIC_METHODS) {

                violationRuntimes.add(
                        new ViolationReport(
                                name(),
                                severity(),
                                String.format(
                                        "Class '%s' exposes %d public methods, exceeding the recommended maximum of %d. Consider splitting it into smaller, more focused classes.",
                                        component.getName(),
                                        publicMethodCount,
                                        MAX_PUBLIC_METHODS
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

    private long countDeclaredPublicMethods(Class<?> targetClass) {
        long count = 0;
        for (Method method : targetClass.getDeclaredMethods()) {
            if (Modifier.isPublic(method.getModifiers()) && !method.isSynthetic() && !method.isBridge()) {
                count++;
            }
        }
        return count;
    }
}
