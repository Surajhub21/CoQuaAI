package com.developersuraj.coquaai.AI;

import com.developersuraj.coquaai.Entity.ViolationReport;

public final class GeminiPromptBuilder {

    private GeminiPromptBuilder() {
    }

    public static String buildPrompt(ViolationReport violation) {

        return """
                You are a Senior Java Backend Architect and Spring Boot Expert.

                A code quality rule has been violated.

                ===========================
                Rule Information
                ===========================

                Rule Name:
                %s

                Severity:
                %s

                Message:
                %s

                File:
                %s

                Line:
                %s

                ===========================

                Explain this issue in the following format.

                ## Why is this an issue?

                ## What problems can it cause?

                ## Bad Example

                ## Correct Example

                ## Best Practice

                ## Additional Notes

                Keep the explanation concise and beginner friendly.

                Return Markdown only.
                """
                .formatted(
                        violation.ruleName(),
                        violation.severity(),
                        violation.message(),
                        violation.file(),
                        violation.line()
                );
    }

}
