package com.developersuraj.coquaai.core.analyzer;

import com.developersuraj.coquaai.Entity.ViolationReport;
import com.developersuraj.coquaai.core.rules.StaticRule;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class StaticCodeScanning {

    private final List<StaticRule> rules;

    public StaticCodeScanning(List<StaticRule> rules) {
        this.rules = rules;
    }

    public List<ViolationReport> scan(Path projectRoot) throws IOException {
        List<ViolationReport> violations = new ArrayList<>();

        Files.walk(projectRoot)
                .filter(p -> p.toString().endsWith(".java"))
                .forEach(file -> {
                    try {
                        CompilationUnit cu = StaticJavaParser.parse(file);
                        for (StaticRule rule : rules) {
                            violations.addAll(rule.analyze(cu, file));
                        }
                    } catch (Exception e) {
                        System.err.println("Failed to parse: " + file);
                    }
                });

        return violations;
    }
}