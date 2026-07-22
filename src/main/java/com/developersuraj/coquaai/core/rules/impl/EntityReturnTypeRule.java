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
import java.util.stream.Collectors;

public class EntityReturnTypeRule implements StaticRule {

    @Override
    public List<ViolationReport> analyze(CompilationUnit cu, Path file) {

        List<ViolationReport> violations = new ArrayList<>();

        Set<String> localEntityTypeNames = cu.findAll(ClassOrInterfaceDeclaration.class).stream()
                .filter(c -> c.isAnnotationPresent("Entity"))
                .map(ClassOrInterfaceDeclaration::getNameAsString)
                .collect(Collectors.toSet());

        cu.findAll(ClassOrInterfaceDeclaration.class).stream()
                .filter(c -> c.isAnnotationPresent("RestController") || c.isAnnotationPresent("Controller"))
                .forEach(controller -> controller.getMethods().forEach(method -> {

                    String returnTypeName = extractInnerTypeName(method.getType().asString());

                    boolean isEntity = localEntityTypeNames.contains(returnTypeName) || returnTypeName.endsWith("Entity");

                    if (!isEntity) {
                        return;
                    }

                    int line = method.getBegin().map(p -> p.line).orElse(-1);

                    violations.add(new ViolationReport(
                            "Entity Returned Directly",
                            Severity.HIGH,
                            String.format(
                                    "Method '%s' in controller '%s' returns entity type '%s' directly instead of a response DTO.",
                                    method.getNameAsString(), controller.getNameAsString(), returnTypeName
                            ),
                            SourceType.STATIC,
                            file.toString(),
                            line
                    ));
                }));

        return violations;
    }

    private String extractInnerTypeName(String typeName) {

        String result = typeName;

        int genericStart = result.indexOf('<');
        if (genericStart != -1) {
            result = result.substring(genericStart + 1, result.length() - 1);
        }

        int lastDot = result.lastIndexOf('.');
        if (lastDot != -1) {
            result = result.substring(lastDot + 1);
        }

        return result.trim();
    }
}
