package com.developersuraj.coquaai.core.engine;

import com.developersuraj.coquaai.Entity.ViolationReport;
import com.developersuraj.coquaai.core.analyzer.StaticCodeScanning;
import com.developersuraj.coquaai.core.rules.StaticRule;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class StaticRuleEngine {

    private final List<StaticRule> rules;

    public StaticRuleEngine(List<StaticRule> rules) {
        this.rules = rules;
    }

    public List<ViolationReport> analyzeProject() throws IOException {

        Path sourceRoot = Paths.get(System.getProperty("user.dir"), "src", "main", "java");

        StaticCodeScanning scanner = new StaticCodeScanning(rules);

        return new ArrayList<>(scanner.scan(sourceRoot));
    }
}
