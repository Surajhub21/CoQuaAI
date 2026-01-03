package com.developersuraj.coquaai.Entity;

public record ViolationReport(
        String ruleName,
        Severity severity,
        String message,
        SourceType sourceType,  // RUNTIME or STATIC
        String file,
        Integer line
) {
}
