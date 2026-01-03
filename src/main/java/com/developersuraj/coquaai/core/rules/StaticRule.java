package com.developersuraj.coquaai.core.rules;

import com.developersuraj.coquaai.Entity.ViolationReport;
import com.github.javaparser.ast.CompilationUnit;

import java.nio.file.Path;
import java.util.List;

public interface StaticRule {

   List<ViolationReport> analyze(CompilationUnit cu, Path file);
}
