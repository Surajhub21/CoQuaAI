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
import java.util.Set;

public class TooManyEndpointsRule implements StaticRule {

    private static final int MAX_ENDPOINTS = 10;

    private static final Set<String> MAPPING_ANNOTATIONS = Set.of(
            "GetMapping", "PostMapping", "PutMapping", "DeleteMapping", "PatchMapping", "RequestMapping"
    );

    @Override
    public List<ViolationReport> analyze(CompilationUnit cu, Path file) {

        List<ViolationReport> violations = new ArrayList<>();

        cu.findAll(ClassOrInterfaceDeclaration.class).stream()
                .filter(c -> c.isAnnotationPresent("RestController") || c.isAnnotationPresent("Controller"))
                .forEach(controller -> {

                    long endpointCount = controller.getMethods().stream()
                            .filter(method -> method.getAnnotations().stream()
                                    .anyMatch(annotation -> MAPPING_ANNOTATIONS.contains(annotation.getNameAsString())))
                            .count();

                    if (endpointCount > MAX_ENDPOINTS) {

                        int line = controller.getBegin().map(p -> p.line).orElse(-1);

                        violations.add(new ViolationReport(
                                "Too Many Endpoints",
                                Severity.MEDIUM,
                                String.format(
                                        "Controller %s declares %d endpoints, exceeding the recommended maximum of %d. Consider splitting it into multiple controllers.",
                                        controller.getNameAsString(), endpointCount, MAX_ENDPOINTS
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
