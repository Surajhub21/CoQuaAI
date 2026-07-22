package com.developersuraj.coquaai.core.rules.impl;

import com.developersuraj.coquaai.Entity.Severity;
import com.developersuraj.coquaai.Entity.SourceType;
import com.developersuraj.coquaai.Entity.ViolationReport;
import com.developersuraj.coquaai.core.rules.StaticRule;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MissingValidatedRequestBodyRule implements StaticRule {

    private static final Set<String> VALIDATION_ANNOTATIONS = Set.of(
            "NotNull", "NotBlank", "NotEmpty", "Size", "Min", "Max",
            "Pattern", "Email", "Positive", "Negative", "Past", "Future", "AssertTrue", "AssertFalse"
    );

    @Override
    public List<ViolationReport> analyze(CompilationUnit cu, Path file) {

        List<ViolationReport> violations = new ArrayList<>();

        cu.findAll(MethodDeclaration.class).forEach(method ->
                method.getParameters().stream()
                        .filter(parameter -> parameter.isAnnotationPresent("RequestBody"))
                        .forEach(parameter -> {

                            if (parameter.isAnnotationPresent("Valid") || parameter.isAnnotationPresent("Validated")) {
                                return;
                            }

                            String dtoTypeName = parameter.getType().asString();

                            if (!dtoHasValidationAnnotations(cu, dtoTypeName)) {
                                return;
                            }

                            int line = parameter.getBegin().map(p -> p.line).orElse(-1);

                            violations.add(new ViolationReport(
                                    "Missing @Validated on Request Body",
                                    Severity.MEDIUM,
                                    String.format(
                                            "Parameter '%s' of type '%s' is annotated @RequestBody but not @Valid/@Validated, even though the DTO declares validation constraints.",
                                            parameter.getNameAsString(),
                                            dtoTypeName
                                    ),
                                    SourceType.STATIC,
                                    file.toString(),
                                    line
                            ));
                        })
        );

        return violations;
    }

    private boolean dtoHasValidationAnnotations(CompilationUnit cu, String dtoTypeName) {

        return cu.findAll(ClassOrInterfaceDeclaration.class).stream()
                .filter(classDeclaration -> classDeclaration.getNameAsString().equals(dtoTypeName))
                .flatMap(classDeclaration -> classDeclaration.findAll(FieldDeclaration.class).stream())
                .anyMatch(field -> VALIDATION_ANNOTATIONS.stream().anyMatch(field::isAnnotationPresent));
    }
}
