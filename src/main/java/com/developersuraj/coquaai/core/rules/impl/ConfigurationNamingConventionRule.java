package com.developersuraj.coquaai.core.rules.impl;

import com.developersuraj.coquaai.Entity.Severity;
import com.developersuraj.coquaai.Entity.SourceType;
import com.developersuraj.coquaai.Entity.ViolationReport;
import com.developersuraj.coquaai.core.rules.StaticRule;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ConfigurationNamingConventionRule implements StaticRule {

    @Override
    public List<ViolationReport> analyze(CompilationUnit cu, Path file) {

        List<ViolationReport> violations = new ArrayList<>();

        cu.findAll(ClassOrInterfaceDeclaration.class).forEach(classDeclaration -> {

            if (!classDeclaration.isAnnotationPresent("Configuration")) {
                return;
            }

            if (!classDeclaration.getNameAsString().endsWith("Configuration")) {

                int line = classDeclaration.getBegin().map(p -> p.line).orElse(-1);

                violations.add(new ViolationReport(
                        "Bean Naming Convention",
                        Severity.LOW,
                        String.format(
                                "Configuration class '%s' should be suffixed with 'Configuration'.",
                                classDeclaration.getNameAsString()
                        ),
                        SourceType.STATIC,
                        file.toString(),
                        line
                ));
            }
        });

        return violations;
    }
}
