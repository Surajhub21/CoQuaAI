package com.developersuraj.coquaai.core.rules.impl;

import com.developersuraj.coquaai.Entity.ComponentInfo;
import com.developersuraj.coquaai.Entity.Severity;
import com.developersuraj.coquaai.Entity.SourceType;
import com.developersuraj.coquaai.Entity.ViolationReport;
import com.developersuraj.coquaai.core.rules.RuntimeRule;
import com.developersuraj.coquaai.core.rules.StaticRule;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.FieldDeclaration;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FieldInjectionRuntimeRule implements StaticRule {

    @Override
    public List<ViolationReport> analyze(CompilationUnit cu, Path file) {

        List<ViolationReport> violations = new ArrayList<>();

        cu.findAll(FieldDeclaration.class).forEach(field -> {
            if (field.isAnnotationPresent("Autowired")) {
                int line = field.getBegin().map(p -> p.line).orElse(-1);

                violations.add(new ViolationReport(
                        "Field Injection"
                        , Severity.HIGH,
                        "Avoid @Autowired on fields. Use constructor injection.",
                        SourceType.STATIC,
                        file.toString(),
                        line
                ));
            }
        });

        return violations;
    }
}
