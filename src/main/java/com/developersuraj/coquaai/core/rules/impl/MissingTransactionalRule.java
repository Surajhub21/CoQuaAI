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
import java.util.Locale;

public class MissingTransactionalRule implements StaticRule {

    private static final List<String> MUTATING_PREFIXES = List.of(
            "save", "update", "delete", "remove", "insert", "create", "persist", "merge", "add"
    );

    @Override
    public List<ViolationReport> analyze(CompilationUnit cu, Path file) {

        List<ViolationReport> violations = new ArrayList<>();

        cu.findAll(ClassOrInterfaceDeclaration.class).forEach(classDeclaration -> {

            if (!classDeclaration.isAnnotationPresent("Service")) {
                return;
            }

            boolean classLevelTransactional = classDeclaration.isAnnotationPresent("Transactional");

            classDeclaration.findAll(MethodDeclaration.class).forEach(method -> {

                if (!method.isPublic() || !isMutatingMethod(method.getNameAsString())) {
                    return;
                }

                if (classLevelTransactional || method.isAnnotationPresent("Transactional")) {
                    return;
                }

                int line = method.getBegin().map(p -> p.line).orElse(-1);

                violations.add(new ViolationReport(
                        "Missing @Transactional",
                        Severity.MEDIUM,
                        String.format(
                                "Method %s in service %s appears to modify data but is not annotated with @Transactional.",
                                method.getNameAsString(),
                                classDeclaration.getNameAsString()
                        ),
                        SourceType.STATIC,
                        file.toString(),
                        line
                ));
            });
        });

        return violations;
    }

    private boolean isMutatingMethod(String methodName) {
        String lowerCaseName = methodName.toLowerCase(Locale.ROOT);
        return MUTATING_PREFIXES.stream().anyMatch(lowerCaseName::startsWith);
    }
}
