package com.developersuraj.coquaai.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AIPageController {

    @GetMapping("/ai-review")
    public String page() {
        return "forward:/ai-review.html";
    }
}
