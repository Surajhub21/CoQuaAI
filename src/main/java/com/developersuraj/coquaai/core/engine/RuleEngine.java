package com.developersuraj.coquaai.core.engine;

import com.developersuraj.coquaai.Entity.ComponentInfo;
import com.developersuraj.coquaai.Entity.Violation;
import com.developersuraj.coquaai.core.rules.Rule;

import java.util.ArrayList;
import java.util.List;


public class RuleEngine {

    private final List<Rule> rules;

    public RuleEngine(List<Rule> rules) {
        this.rules = rules;
    }

    public List<Violation> evaluate(
            List<ComponentInfo> components) {

        List<Violation> allViolations = new ArrayList<>();

        for (Rule rule : rules) {
            allViolations.addAll(
                    rule.evaluate(components));
        }

        return allViolations;
    }
}
