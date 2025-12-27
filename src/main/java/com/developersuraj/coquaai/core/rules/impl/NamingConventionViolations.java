package com.developersuraj.coquaai.core.rules.impl;

import com.developersuraj.coquaai.Entity.ComponentInfo;
import com.developersuraj.coquaai.Entity.ComponentType;
import com.developersuraj.coquaai.Entity.Severity;
import com.developersuraj.coquaai.Entity.Violation;
import com.developersuraj.coquaai.core.rules.Rule;

import java.util.ArrayList;
import java.util.List;

public class NamingConventionViolations implements Rule {
    @Override
    public String name() {
        return "Naming Convention Violations";
    }

    @Override
    public Severity severity() {
        return Severity.LOW;
    }

    @Override
    public List<Violation> evaluate(List<ComponentInfo> components) {
        List<Violation> violations = new ArrayList<>();

        for (ComponentInfo component : components) {

            if(component.getType() == ComponentType.CONTROLLER){

                if(!component.getName().contains("Controller")){
                    violations.add(
                            new Violation(
                                    name(),
                                    severity(),
                                    String.format(
                                            "Controller '%s' with no 'Controller suffix'."
                                            ,component.getName()
                                    )
                            )
                    );
                }
            }
            else if(component.getType() == ComponentType.SERVICE){
                if(!component.getName().contains("Service")){
                    violations.add(
                            new Violation(
                                    name(),
                                    severity(),
                                    String.format(
                                            "Service '%s' not ending in 'Service'.",
                                            component.getName()
                                    )
                            )
                    );
                }
            }
            else if(component.getType() == ComponentType.REPOSITORY){
                if(!component.getName().contains("Repository")){
                    violations.add(
                            new Violation(
                                    name(),
                                    severity(),
                                    String.format(
                                            "Repository '%s' not ending with 'Repository'.",
                                            component.getName()
                                    )
                            )
                    );
                }
            }
        }

        return violations;
    }
}
