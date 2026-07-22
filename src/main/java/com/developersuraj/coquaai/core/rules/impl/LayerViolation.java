package com.developersuraj.coquaai.core.rules.impl;

import com.developersuraj.coquaai.Entity.*;
import com.developersuraj.coquaai.core.rules.RuntimeRule;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

public class LayerViolation implements RuntimeRule {


    @Override
    public String name() {
        return "Controller Independence Rule";
    }

    @Override
    public Severity severity() {
        return Severity.HIGH;
    }

    @Override
    public List<ViolationReport> evaluate(List<ComponentInfo> components) {

        List<ViolationReport> violationRuntimes = new ArrayList<>();

        for (ComponentInfo component : components) {

            if(component.getType() == ComponentType.CONTROLLER){

                for (Class<?> dep : component.getDependencies()) {

                    if (Repository.class.isAssignableFrom(dep)) {

                        violationRuntimes.add(
                                new ViolationReport(
                                        name(),
                                        severity(),
                                        String.format(
                                                "Controller '%s' directly depends on Repository '%s'"
                                                , component.getName()
                                                , dep.getSimpleName()
                                        ),
                                        SourceType.RUNTIME,
                                        component.getName(),
                                        null
                                ));
                    }
                }
            }
            else if (component.getType() == ComponentType.SERVICE) {

                for(Class<?> dep : component.getDependencies()){

                    if (dep.isAnnotationPresent(Controller.class) || dep.isAnnotationPresent(RestController.class)) {

                        violationRuntimes.add(
                                new ViolationReport(
                                        name(),
                                        severity(),
                                        String.format(
                                                "Service '%s' should not be depends on Controller '%s'"
                                                , component.getName()
                                                , dep.getSimpleName()
                                        ),
                                        SourceType.RUNTIME,
                                        component.getName(),
                                        null
                                ));
                    }
                }
            }
            else {
                for(Class<?> dep : component.getDependencies()){

                    if (dep.isAnnotationPresent(Service.class)) {

                        violationRuntimes.add(
                                new ViolationReport(
                                        name(),
                                        severity(),
                                        String.format(
                                                "Repository '%s' should not be depends on Service '%s'"
                                                , component.getName()
                                                , dep.getSimpleName()
                                        ),
                                        SourceType.RUNTIME,
                                        component.getName(),
                                        null
                                ));
                    }
                }
            }
        }
        return violationRuntimes;
    }
}
