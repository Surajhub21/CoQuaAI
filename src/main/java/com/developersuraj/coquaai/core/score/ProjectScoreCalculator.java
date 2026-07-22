package com.developersuraj.coquaai.core.score;

import com.developersuraj.coquaai.Entity.Severity;
import com.developersuraj.coquaai.Entity.ViolationReport;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ProjectScoreCalculator {

    private static final int STARTING_SCORE = 100;
    private static final int HIGH_PENALTY = 8;
    private static final int MEDIUM_PENALTY = 4;
    private static final int LOW_PENALTY = 2;

    private static final String ARCHITECTURE = "Architecture";
    private static final String SPRING = "Spring";
    private static final String REST = "REST";
    private static final String CODE_SMELLS = "Code Smells";

    public ProjectScoreReport calculate(List<ViolationReport> violations) {

        Map<String, Integer> scores = new LinkedHashMap<>();
        Map<String, Integer> counts = new LinkedHashMap<>();

        for (String category : List.of(ARCHITECTURE, SPRING, REST, CODE_SMELLS)) {
            scores.put(category, STARTING_SCORE);
            counts.put(category, 0);
        }

        for (ViolationReport violation : violations) {

            String category = categorize(violation.ruleName());
            int penalty = penaltyFor(violation.severity());

            scores.merge(category, -penalty, Integer::sum);
            counts.merge(category, 1, Integer::sum);
        }

        List<ProjectScoreReport.CategoryScore> categoryScores = scores.entrySet().stream()
                .map(entry -> new ProjectScoreReport.CategoryScore(
                        entry.getKey(),
                        clamp(entry.getValue()),
                        counts.get(entry.getKey())
                ))
                .toList();

        int overall = (int) Math.round(
                categoryScores.stream().mapToInt(ProjectScoreReport.CategoryScore::score).average().orElse(100)
        );

        return new ProjectScoreReport(overall, categoryScores);
    }

    private int penaltyFor(Severity severity) {
        return switch (severity) {
            case HIGH -> HIGH_PENALTY;
            case MEDIUM -> MEDIUM_PENALTY;
            case LOW -> LOW_PENALTY;
        };
    }

    private int clamp(int score) {
        return Math.max(0, Math.min(100, score));
    }

    private String categorize(String ruleName) {

        String name = ruleName == null ? "" : ruleName;

        if (containsAny(name, "Transactional", "Validated", "Duplicate Request Mapping", "Naming", "Field Injection")) {
            return SPRING;
        }

        if (containsAny(name, "DTO", "Entity Returned", "ResponseEntity", "Endpoint", "Request Mapping")) {
            return REST;
        }

        if (containsAny(name, "Long Method", "Catch Block", "System.out", "TODO/FIXME", "Magic Number")) {
            return CODE_SMELLS;
        }

        return ARCHITECTURE;
    }

    private boolean containsAny(String value, String... candidates) {
        for (String candidate : candidates) {
            if (value.contains(candidate)) {
                return true;
            }
        }
        return false;
    }
}
