package com.developersuraj.coquaai.core.score;

import java.util.List;

public record ProjectScoreReport(
        int overallScore,
        List<CategoryScore> categories
) {

    public record CategoryScore(
            String category,
            int score,
            int violationCount
    ) {
    }
}
