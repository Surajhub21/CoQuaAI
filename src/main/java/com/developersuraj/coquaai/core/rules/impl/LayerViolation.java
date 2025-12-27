package com.developersuraj.coquaai.core.rules.impl;

import com.developersuraj.coquaai.Entity.ComponentInfo;
import com.developersuraj.coquaai.Entity.ComponentType;
import com.developersuraj.coquaai.Entity.Severity;
import com.developersuraj.coquaai.Entity.Violation;
import com.developersuraj.coquaai.core.rules.Rule;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

public class LayerViolation implements Rule {


    @Override
    public String name() {
        return "Controller Independence Rule";
    }

    @Override
    public Severity severity() {
        return Severity.HIGH;
    }

    @Override
    public List<Violation> evaluate(List<ComponentInfo> components) {

        List<Violation> violations = new ArrayList<>();

        for (ComponentInfo component : components) {

            if(component.getType() == ComponentType.CONTROLLER){

                for (Class<?> dep : component.getDependencies()) {

                    if (org.springframework.data.repository.Repository
                            .class.isAssignableFrom(dep)) {

                        violations.add(
                                new Violation(
                                        name(),
                                        severity(),
                                        String.format(
                                                "Controller '%s' directly depends on Repository '%s'"
                                                , component.getName()
                                                , dep.getSimpleName()
                                        )
                                ));
                    }
                }
            }
            else if (component.getType() == ComponentType.SERVICE) {

                for(Class<?> dep : component.getDependencies()){

                    if (dep.isAnnotationPresent(Controller.class) || dep.isAnnotationPresent(RestController.class)) {

                        violations.add(
                                new Violation(
                                        name(),
                                        severity(),
                                        String.format(
                                                "Service '%s' should not be depends on Controller '%s'"
                                                , component.getName()
                                                , dep.getSimpleName()
                                        )
                                ));
                    }
                }
            }
            else {
                for(Class<?> dep : component.getDependencies()){

                    if (dep.isAnnotationPresent(Service.class)) {

                        violations.add(
                                new Violation(
                                        name(),
                                        severity(),
                                        String.format(
                                                "Repository '%s' should not be depends on Service '%s'"
                                                , component.getName()
                                                , dep.getSimpleName()
                                        )
                                ));
                    }
                }
            }
        }
        return violations;
    }
}
