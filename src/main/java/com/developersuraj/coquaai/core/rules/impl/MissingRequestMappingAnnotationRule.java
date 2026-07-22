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

public class MissingRequestMappingAnnotationRule implements StaticRule {

    private static final Set<String> MAPPING_ANNOTATIONS = Set.of(
            "GetMapping", "PostMapping", "PutMapping", "DeleteMapping", "PatchMapping", "RequestMapping"
    );

    @Override
    public List<ViolationReport> analyze(CompilationUnit cu, Path file) {

        List<ViolationReport> violations = new ArrayList<>();

        cu.findAll(ClassOrInterfaceDeclaration.class).stream()
                .filter(c -> c.isAnnotationPresent("RestController") || c.isAnnotationPresent("Controller"))
                .forEach(controller -> controller.getMethods().stream()
                        .filter(method -> method.isPublic() && !method.isStatic())
                        .filter(method -> method.getAnnotations().stream()
                                .noneMatch(annotation -> MAPPING_ANNOTATIONS.contains(annotation.getNameAsString())))
                        .forEach(method -> {

                            int line = method.getBegin().map(p -> p.line).orElse(-1);

                            violations.add(new ViolationReport(
                                    "Missing Request Mapping",
                                    Severity.MEDIUM,
                                    String.format(
                                            "Public method '%s' in controller '%s' has no @RequestMapping/@GetMapping/etc. annotation.",
                                            method.getNameAsString(), controller.getNameAsString()
                                    ),
                                    SourceType.STATIC,
                                    file.toString(),
                                    line
                            ));
                        }));

        return violations;
    }
}
