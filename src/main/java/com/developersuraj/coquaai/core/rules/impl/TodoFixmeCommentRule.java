package com.developersuraj.coquaai.core.rules.impl;

import com.developersuraj.coquaai.Entity.Severity;
import com.developersuraj.coquaai.Entity.SourceType;
import com.developersuraj.coquaai.Entity.ViolationReport;
import com.developersuraj.coquaai.core.rules.StaticRule;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.comments.Comment;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TodoFixmeCommentRule implements StaticRule {

    @Override
    public List<ViolationReport> analyze(CompilationUnit cu, Path file) {

        List<ViolationReport> violations = new ArrayList<>();

        for (Comment comment : cu.getAllContainedComments()) {

            String content = comment.getContent().toUpperCase(Locale.ROOT);

            if (!content.contains("TODO") && !content.contains("FIXME")) {
                continue;
            }

            int line = comment.getBegin().map(p -> p.line).orElse(-1);

            violations.add(new ViolationReport(
                    "TODO/FIXME Comment",
                    Severity.LOW,
                    "Unresolved TODO/FIXME comment found: " + comment.getContent().trim(),
                    SourceType.STATIC,
                    file.toString(),
                    line
            ));
        }

        return violations;
    }
}
