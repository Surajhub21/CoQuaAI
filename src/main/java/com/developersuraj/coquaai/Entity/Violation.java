package com.developersuraj.coquaai.Entity;

public class Violation {

    private final String ruleName;
    private final Severity severity;
    private final String message;

    public Violation(
            String ruleName,
            Severity severity,
            String message) {
        this.ruleName = ruleName;
        this.severity = severity;
        this.message = message;
    }

    public String getRuleName() {
        return ruleName;
    }

    public Severity getSeverity() {
        return severity;
    }

    public String getMessage() {
        return message;
    }
}
