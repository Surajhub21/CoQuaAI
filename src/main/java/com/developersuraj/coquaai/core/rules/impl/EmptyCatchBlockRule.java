package com.developersuraj.coquaai.core.rules.impl;

import com.developersuraj.coquaai.Entity.Severity;
import com.developersuraj.coquaai.Entity.SourceType;
import com.developersuraj.coquaai.Entity.ViolationReport;
import com.developersuraj.coquaai.core.rules.StaticRule;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.stmt.CatchClause;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class EmptyCatchBlockRule implements StaticRule {

    @Override
    public List<ViolationReport> analyze(CompilationUnit cu, Path file) {

        List<ViolationReport> violations = new ArrayList<>();

        cu.findAll(CatchClause.class).forEach(catchClause -> {

            if (!catchClause.getBody().getStatements().isEmpty()) {
                return;
            }

            int line = catchClause.getBegin().map(p -> p.line).orElse(-1);

            violations.add(new ViolationReport(
                    "Empty Catch Block",
                    Severity.HIGH,
                    String.format(
                            "Catch block for '%s' is empty, silently swallowing the exception.",
                            catchClause.getParameter().getType().asString()
                    ),
                    SourceType.STATIC,
                    file.toString(),
                    line
            ));
        });

        return violations;
    }
}
