package com.developersuraj.coquaai.core.rules.impl;

import com.developersuraj.coquaai.Entity.Severity;
import com.developersuraj.coquaai.Entity.SourceType;
import com.developersuraj.coquaai.Entity.ViolationReport;
import com.developersuraj.coquaai.core.rules.StaticRule;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class DuplicateRequestMappingRule implements StaticRule {

    private static final Set<String> MAPPING_ANNOTATIONS = Set.of(
            "GetMapping", "PostMapping", "PutMapping", "DeleteMapping", "PatchMapping", "RequestMapping"
    );

    @Override
    public List<ViolationReport> analyze(CompilationUnit cu, Path file) {

        List<ViolationReport> violations = new ArrayList<>();

        cu.findAll(ClassOrInterfaceDeclaration.class).forEach(classDeclaration -> {

            Map<String, String> seenMappings = new HashMap<>();

            classDeclaration.getMethods().forEach(method ->
                    method.getAnnotations().stream()
                            .filter(annotation -> MAPPING_ANNOTATIONS.contains(annotation.getNameAsString()))
                            .forEach(annotation -> {

                                String key = annotation.getNameAsString() + ":" + extractPath(annotation);
                                int line = method.getBegin().map(p -> p.line).orElse(-1);

                                if (seenMappings.containsKey(key)) {
                                    violations.add(new ViolationReport(
                                            "Duplicate Request Mapping",
                                            Severity.HIGH,
                                            String.format(
                                                    "Method %s in class %s duplicates the mapping %s already declared by method %s.",
                                                    method.getNameAsString(),
                                                    classDeclaration.getNameAsString(),
                                                    key,
                                                    seenMappings.get(key)
                                            ),
                                            SourceType.STATIC,
                                            file.toString(),
                                            line
                                    ));
                                } else {
                                    seenMappings.put(key, method.getNameAsString());
                                }
                            })
            );
        });

        return violations;
    }

    private String extractPath(AnnotationExpr annotation) {

        if (annotation instanceof SingleMemberAnnotationExpr singleMember) {
            return singleMember.getMemberValue().toString();
        }

        if (annotation instanceof NormalAnnotationExpr normal) {
            Optional<MemberValuePair> pathPair = normal.getPairs().stream()
                    .filter(pair -> pair.getNameAsString().equals("path") || pair.getNameAsString().equals("value"))
                    .findFirst();

            if (pathPair.isPresent()) {
                Expression value = pathPair.get().getValue();
                return value.toString();
            }
        }

        return "\"\"";
    }
}
