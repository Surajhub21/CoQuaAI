package com.developersuraj.coquaai.core.rules.impl;

import com.developersuraj.coquaai.Entity.ComponentInfo;
import com.developersuraj.coquaai.Entity.Severity;
import com.developersuraj.coquaai.Entity.SourceType;
import com.developersuraj.coquaai.Entity.ViolationReport;
import com.developersuraj.coquaai.core.rules.RuntimeRule;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class NoFieldInjectionRuntimeRule implements RuntimeRule {

    @Override
    public String name() {
        return "Field Injection Detected";
    }

    @Override
    public Severity severity() {
        return Severity.MEDIUM;
    }

    @Override
    public List<ViolationReport> evaluate(List<ComponentInfo> components) {

        List<ViolationReport> violationRuntimes = new ArrayList<>();

        for (ComponentInfo component : components) {

            Class<?> clazz = component.getTargetClass();

            for (Field field : clazz.getDeclaredFields()) {

                if (field.isAnnotationPresent(Autowired.class)) {

                    violationRuntimes.add(
                            new ViolationReport(
                                    name(),
                                    severity(),
                                    component.getName()
                                            + " uses field injection: "
                                            + field.getName(),
                            SourceType.RUNTIME,
                            component.getName(),
                            null
                            )
                    );
                }
            }
        }

        return violationRuntimes;
    }
}
