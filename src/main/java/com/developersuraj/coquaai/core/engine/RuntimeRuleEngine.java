package com.developersuraj.coquaai.core.engine;

import com.developersuraj.coquaai.Entity.ComponentInfo;
import com.developersuraj.coquaai.Entity.ViolationReport;
import com.developersuraj.coquaai.core.rules.RuntimeRule;

import java.util.ArrayList;
import java.util.List;


public class RuntimeRuleEngine {

    private final List<RuntimeRule> runtimeRules;

    public RuntimeRuleEngine(List<RuntimeRule> runtimeRules) {
        this.runtimeRules = runtimeRules;
    }

    public List<ViolationReport> evaluate(
            List<ComponentInfo> components) {

        List<ViolationReport> allViolationRuntimes = new ArrayList<>();

        for (RuntimeRule runtimeRule : runtimeRules) {
            allViolationRuntimes.addAll(
                    runtimeRule.evaluate(components));
        }

        return allViolationRuntimes;
    }
}
