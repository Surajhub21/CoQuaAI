package com.developersuraj.coquaai.AI.dto;

import com.developersuraj.coquaai.Entity.Severity;

public record AiRequest(

        String ruleName,

        Severity severity,

        String message,

        String file,

        Integer line

) {
}
