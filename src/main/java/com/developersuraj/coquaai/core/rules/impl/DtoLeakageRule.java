package com.developersuraj.coquaai.core.rules.impl;

import com.developersuraj.coquaai.Entity.Severity;
import com.developersuraj.coquaai.Entity.SourceType;
import com.developersuraj.coquaai.Entity.ViolationReport;
import com.developersuraj.coquaai.core.rules.StaticRule;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Flags controller methods that accept or return a type recognized as a JPA entity.
 * Entities declared in the same file are detected precisely via the @Entity annotation.
 * Entities declared elsewhere fall back to a naming heuristic (type name ends with "Entity"),
 * since single-file static analysis has no cross-file type resolution.
 */
public class DtoLeakageRule implements StaticRule {

    @Override
    public List<ViolationReport> analyze(CompilationUnit cu, Path file) {

        List<ViolationReport> violations = new ArrayList<>();

        boolean isControllerFile = cu.findAll(ClassOrInterfaceDeclaration.class).stream()
                .anyMatch(c -> c.isAnnotationPresent("RestController") || c.isAnnotationPresent("Controller"));

        if (!isControllerFile) {
            return violations;
        }

        Set<String> localEntityTypeNames = cu.findAll(ClassOrInterfaceDeclaration.class).stream()
                .filter(c -> c.isAnnotationPresent("Entity"))
                .map(ClassOrInterfaceDeclaration::getNameAsString)
                .collect(java.util.stream.Collectors.toSet());

        cu.findAll(ClassOrInterfaceDeclaration.class).stream()
                .filter(c -> c.isAnnotationPresent("RestController") || c.isAnnotationPresent("Controller"))
                .forEach(controller -> controller.getMethods().forEach(method -> {

                    for (Parameter parameter : method.getParameters()) {
                        checkTypeAndReport(violations, file, controller.getNameAsString(),
                                method.getNameAsString(), parameter.getType().asString(),
                                localEntityTypeNames, "accepts");
                    }

                    checkTypeAndReport(violations, file, controller.getNameAsString(),
                            method.getNameAsString(), method.getType().asString(),
                            localEntityTypeNames, "returns");
                }));

        return violations;
    }

    private void checkTypeAndReport(List<ViolationReport> violations, Path file, String controllerName,
                                     String methodName, String typeName, Set<String> localEntityTypeNames,
                                     String direction) {

        String simpleTypeName = extractInnerTypeName(typeName);

        if (!isLikelyEntity(simpleTypeName, localEntityTypeNames)) {
            return;
        }

        violations.add(new ViolationReport(
                "DTO Leakage",
                Severity.HIGH,
                String.format(
                        "Method %s in controller %s %s the entity type %s directly. Expose a dedicated DTO instead.",
                        methodName, controllerName, direction, simpleTypeName
                ),
                SourceType.STATIC,
                file.toString(),
                null
        ));
    }

    private boolean isLikelyEntity(String simpleTypeName, Set<String> localEntityTypeNames) {
        return localEntityTypeNames.contains(simpleTypeName) || simpleTypeName.endsWith("Entity");
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
