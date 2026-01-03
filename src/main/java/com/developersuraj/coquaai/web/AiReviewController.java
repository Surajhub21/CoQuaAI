package com.developersuraj.coquaai.web;

import com.developersuraj.coquaai.Entity.ComponentInfo;
import com.developersuraj.coquaai.Entity.ViolationReport;
import com.developersuraj.coquaai.core.analyzer.SpringContextScanner;
import com.developersuraj.coquaai.core.engine.RuntimeRuleEngine;
import com.developersuraj.coquaai.core.engine.StaticRuleEngine;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/ai-review")
public class AiReviewController {

    private final SpringContextScanner runtimCodeScanning;
    private final RuntimeRuleEngine runtimeRuleEngine;

    private StaticRuleEngine staticRuleEngine;

    public AiReviewController(SpringContextScanner runtimCodeScanning, RuntimeRuleEngine runtimeRuleEngine, StaticRuleEngine staticRuleEngine) {
        this.runtimCodeScanning = runtimCodeScanning;
        this.runtimeRuleEngine = runtimeRuleEngine;
        this.staticRuleEngine = staticRuleEngine;
    }

    @GetMapping("/report")
    public ResponseEntity<List<?>> report() {

        try {
            List<ViolationReport> reports = new ArrayList<>();

            List<ComponentInfo> scan = runtimCodeScanning.scan();
            reports.addAll(runtimeRuleEngine.evaluate(scan));
            reports.addAll(staticRuleEngine.analyzeProject());

            return ResponseEntity.ok(reports);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

