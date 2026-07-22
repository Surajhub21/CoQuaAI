package com.developersuraj.coquaai.core.rules.impl;

import com.developersuraj.coquaai.Entity.Severity;
import com.developersuraj.coquaai.Entity.SourceType;
import com.developersuraj.coquaai.Entity.ViolationReport;
import com.developersuraj.coquaai.core.rules.StaticRule;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.MethodCallExpr;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SystemOutUsageRule implements StaticRule {

    private static final Set<String> PRINT_METHOD_NAMES = Set.of("println", "print", "printf");
    private static final Set<String> PRINT_STREAM_SCOPES = Set.of("System.out", "System.err");

    @Override
    public List<ViolationReport> analyze(CompilationUnit cu, Path file) {

        List<ViolationReport> violations = new ArrayList<>();

        cu.findAll(MethodCallExpr.class).forEach(call -> {

            if (!PRINT_METHOD_NAMES.contains(call.getNameAsString())) {
                return;
            }

            String scope = call.getScope().map(Object::toString).orElse("");

            if (!PRINT_STREAM_SCOPES.contains(scope)) {
                return;
            }

            int line = call.getBegin().map(p -> p.line).orElse(-1);

            violations.add(new ViolationReport(
                    "System.out Usage",
                    Severity.LOW,
                    String.format("'%s.%s' should be replaced with a proper logging framework (e.g. SLF4J).", scope, call.getNameAsString()),
                    SourceType.STATIC,
                    file.toString(),
                    line
            ));
        });

        return violations;
    }
}
