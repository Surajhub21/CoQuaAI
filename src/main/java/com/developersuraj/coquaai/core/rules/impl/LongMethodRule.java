package com.developersuraj.coquaai.core.rules.impl;

import com.developersuraj.coquaai.Entity.Severity;
import com.developersuraj.coquaai.Entity.SourceType;
import com.developersuraj.coquaai.Entity.ViolationReport;
import com.developersuraj.coquaai.core.rules.StaticRule;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class LongMethodRule implements StaticRule {

    private static final int MAX_METHOD_LINES = 50;

    @Override
    public List<ViolationReport> analyze(CompilationUnit cu, Path file) {

        List<ViolationReport> violations = new ArrayList<>();

        cu.findAll(MethodDeclaration.class).forEach(method -> {

            if (method.getBegin().isEmpty() || method.getEnd().isEmpty()) {
                return;
            }

            int lineCount = method.getEnd().get().line - method.getBegin().get().line + 1;

            if (lineCount > MAX_METHOD_LINES) {

                violations.add(new ViolationReport(
                        "Long Method",
                        Severity.MEDIUM,
                        String.format(
                                "Method '%s' spans %d lines, exceeding the recommended maximum of %d. Consider extracting smaller methods.",
                                method.getNameAsString(), lineCount, MAX_METHOD_LINES
                        ),
                        SourceType.STATIC,
                        file.toString(),
                        method.getBegin().get().line
                ));
            }
        });

        return violations;
    }
}
