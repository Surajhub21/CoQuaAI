package com.developersuraj.coquaai.core.rules.impl;

import com.developersuraj.coquaai.Entity.*;
import com.developersuraj.coquaai.core.rules.RuntimeRule;

import java.util.ArrayList;
import java.util.List;

public class NamingConventionViolations implements RuntimeRule {

    @Override
    public String name() {
        return "Naming Convention Violations";
    }

    @Override
    public Severity severity() {
        return Severity.LOW;
    }

    @Override
    public List<ViolationReport> evaluate(List<ComponentInfo> components) {
        List<ViolationReport> violationRuntimes = new ArrayList<>();

        for (ComponentInfo component : components) {

            if(component.getType() == ComponentType.CONTROLLER){

                if(!component.getName().contains("Controller")){
                    violationRuntimes.add(
                            new ViolationReport(
                                    name(),
                                    severity(),
                                    String.format(
                                            "Controller '%s' with no 'Controller suffix'."
                                            ,component.getName()
                                    ),
                                    SourceType.RUNTIME,
                                    component.getName(),
                                    null
                            )
                    );
                }
            }
            else if(component.getType() == ComponentType.SERVICE){
                if(!component.getName().contains("Service")){
                    violationRuntimes.add(
                            new ViolationReport(
                                    name(),
                                    severity(),
                                    String.format(
                                            "Service '%s' not ending in 'Service'.",
                                            component.getName()
                                    ),
                                    SourceType.RUNTIME,
                                    component.getName(),
                                    null
                            )
                    );
                }
            }
            else if(component.getType() == ComponentType.REPOSITORY){
                if(!component.getName().contains("Repository")){
                    violationRuntimes.add(
                            new ViolationReport(
                                    name(),
                                    severity(),
                                    String.format(
                                            "Repository '%s' not ending with 'Repository'.",
                                            component.getName()
                                    ),
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
