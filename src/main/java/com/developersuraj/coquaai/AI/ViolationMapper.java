package com.developersuraj.coquaai.AI;

import com.developersuraj.coquaai.AI.dto.AiRequest;
import com.developersuraj.coquaai.Entity.ViolationReport;

public final class ViolationMapper {

    private ViolationMapper() {
    }

    public static ViolationReport toViolation(AiRequest request) {

        return new ViolationReport(

                request.ruleName(),

                request.severity(),

                request.message(),

                null,

                request.file(),

                request.line()

        );

    }

}
