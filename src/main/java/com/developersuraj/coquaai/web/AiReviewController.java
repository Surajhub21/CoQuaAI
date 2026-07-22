package com.developersuraj.coquaai.web;

import com.developersuraj.coquaai.AI.GeminiService;
import com.developersuraj.coquaai.AI.ViolationMapper;
import com.developersuraj.coquaai.AI.dto.AiRequest;
import com.developersuraj.coquaai.AI.dto.AiResponse;
import com.developersuraj.coquaai.Entity.ComponentInfo;
import com.developersuraj.coquaai.Entity.ViolationReport;
import com.developersuraj.coquaai.core.analyzer.SpringContextScanner;
import com.developersuraj.coquaai.core.engine.RuntimeRuleEngine;
import com.developersuraj.coquaai.core.engine.StaticRuleEngine;
import com.developersuraj.coquaai.core.score.ProjectScoreCalculator;
import com.developersuraj.coquaai.core.score.ProjectScoreReport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/ai-review")
public class AiReviewController {

    private final SpringContextScanner runtimCodeScanning;
    private final RuntimeRuleEngine runtimeRuleEngine;
    private final StaticRuleEngine staticRuleEngine;
    private final ProjectScoreCalculator projectScoreCalculator;
    private final GeminiService geminiService;

    public AiReviewController(SpringContextScanner runtimCodeScanning, RuntimeRuleEngine runtimeRuleEngine, StaticRuleEngine staticRuleEngine, ProjectScoreCalculator projectScoreCalculator, GeminiService geminiService) {
        this.runtimCodeScanning = runtimCodeScanning;
        this.runtimeRuleEngine = runtimeRuleEngine;
        this.staticRuleEngine = staticRuleEngine;
        this.projectScoreCalculator = projectScoreCalculator;
        this.geminiService = geminiService;
    }

    @GetMapping("/report")
    public ResponseEntity<List<?>> report() {

        try {
            return ResponseEntity.ok(collectViolations());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/explain")
    public AiResponse explain(
            @RequestBody AiRequest request
    ) {

        ViolationReport violation =
                ViolationMapper.toViolation(request);

        String explanation =
                geminiService.explain(violation);

        return new AiResponse(explanation);

    }

    @GetMapping("/score")
    public ResponseEntity<ProjectScoreReport> score() {

        try {
            return ResponseEntity.ok(projectScoreCalculator.calculate(collectViolations()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<ViolationReport> collectViolations() throws IOException {

        List<ViolationReport> reports = new ArrayList<>();

        List<ComponentInfo> scan = runtimCodeScanning.scan();
        reports.addAll(runtimeRuleEngine.evaluate(scan));
        reports.addAll(staticRuleEngine.analyzeProject());

        return reports;
    }

}
