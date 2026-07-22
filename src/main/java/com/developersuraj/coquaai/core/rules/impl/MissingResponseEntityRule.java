package com.developersuraj.coquaai.core.rules.impl;

import com.developersuraj.coquaai.Entity.Severity;
import com.developersuraj.coquaai.Entity.SourceType;
import com.developersuraj.coquaai.Entity.ViolationReport;
import com.developersuraj.coquaai.core.rules.StaticRule;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MissingResponseEntityRule implements StaticRule {

    private static final Set<String> MAPPING_ANNOTATIONS = Set.of(
            "GetMapping", "PostMapping", "PutMapping", "DeleteMapping", "PatchMapping", "RequestMapping"
    );

    @Override
    public List<ViolationReport> analyze(CompilationUnit cu, Path file) {

        List<ViolationReport> violations = new ArrayList<>();

        cu.findAll(ClassOrInterfaceDeclaration.class).stream()
                .filter(c -> c.isAnnotationPresent("RestController") || c.isAnnotationPresent("Controller"))
                .forEach(controller -> controller.getMethods().stream()
                        .filter(this::isEndpointMethod)
                        .forEach(method -> {

                            if (method.getType().asString().startsWith("ResponseEntity")) {
                                return;
                            }

                            int line = method.getBegin().map(p -> p.line).orElse(-1);

                            violations.add(new ViolationReport(
                                    "Missing ResponseEntity",
                                    Severity.LOW,
                                    String.format(
                                            "Endpoint method '%s' in controller '%s' returns '%s' instead of ResponseEntity, limiting control over HTTP status and headers.",
                                            method.getNameAsString(), controller.getNameAsString(), method.getType().asString()
                                    ),
                                    SourceType.STATIC,
                                    file.toString(),
                                    line
                            ));
                        }));

        return violations;
    }

    private boolean isEndpointMethod(MethodDeclaration method) {
        return method.getAnnotations().stream()
                .anyMatch(annotation -> MAPPING_ANNOTATIONS.contains(annotation.getNameAsString()));
    }
}
