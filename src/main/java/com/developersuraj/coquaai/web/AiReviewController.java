package com.developersuraj.coquaai.web;

import com.developersuraj.coquaai.Entity.ComponentInfo;
import com.developersuraj.coquaai.Entity.Violation;
import com.developersuraj.coquaai.core.analyzer.SpringContextScanner;
import com.developersuraj.coquaai.core.engine.RuleEngine;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ai-review")
public class AiReviewController {

    private final SpringContextScanner scanner;
    private final RuleEngine ruleEngine;

    public AiReviewController(
            SpringContextScanner scanner,
            RuleEngine ruleEngine
    ) {
        this.scanner = scanner;
        this.ruleEngine = ruleEngine;
    }

    @GetMapping("/report")
    public List<Violation> report() {

        List<ComponentInfo> facts = scanner.scan();
        return ruleEngine.evaluate(facts);
    }

}

