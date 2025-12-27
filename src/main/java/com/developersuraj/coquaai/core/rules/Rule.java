package com.developersuraj.coquaai.core.rules;

import com.developersuraj.coquaai.Entity.ComponentInfo;
import com.developersuraj.coquaai.Entity.Severity;
import com.developersuraj.coquaai.Entity.Violation;

import java.util.List;

public interface Rule {

    String name();

    Severity severity();

    List<Violation> evaluate(List<ComponentInfo> components);
}
