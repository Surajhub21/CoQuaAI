package com.developersuraj.coquaai.core.rules.impl;

import com.developersuraj.coquaai.Entity.Severity;
import com.developersuraj.coquaai.Entity.SourceType;
import com.developersuraj.coquaai.Entity.ViolationReport;
import com.developersuraj.coquaai.core.rules.StaticRule;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MagicNumberRule implements StaticRule {

    private static final Set<String> ALLOWED_VALUES = Set.of("-1", "0", "1", "2");

    @Override
    public List<ViolationReport> analyze(CompilationUnit cu, Path file) {

        List<ViolationReport> violations = new ArrayList<>();

        cu.findAll(IntegerLiteralExpr.class).forEach(literal -> {

            if (ALLOWED_VALUES.contains(literal.getValue())) {
                return;
            }

            if (literal.findAncestor(AnnotationExpr.class).isPresent()) {
                return;
            }

            if (isNamedConstant(literal)) {
                return;
            }

            int line = literal.getBegin().map(p -> p.line).orElse(-1);

            violations.add(new ViolationReport(
                    "Magic Number",
                    Severity.LOW,
                    String.format("Magic number %s should be extracted into a named constant.", literal.getValue()),
                    SourceType.STATIC,
                    file.toString(),
                    line
            ));
        });

        return violations;
    }

    private boolean isNamedConstant(IntegerLiteralExpr literal) {

        return literal.findAncestor(FieldDeclaration.class)
                .map(field -> field.hasModifier(Modifier.Keyword.STATIC) && field.hasModifier(Modifier.Keyword.FINAL))
                .orElse(false);
    }
}
