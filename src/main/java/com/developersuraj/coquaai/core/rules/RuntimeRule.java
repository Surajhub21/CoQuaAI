package com.developersuraj.coquaai.core.rules;

import com.developersuraj.coquaai.Entity.ComponentInfo;
import com.developersuraj.coquaai.Entity.Severity;
import com.developersuraj.coquaai.Entity.ViolationReport;

import java.util.List;

public interface RuntimeRule {

    String name();

    Severity severity();

    List<ViolationReport> evaluate(List<ComponentInfo> components);
}
