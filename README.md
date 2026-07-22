# How CoQuaAI Works

## Overview

CoQuaAI is a lightweight Spring Boot Starter that analyzes the structure and quality of a Spring Boot application. It performs automated code quality checks using both **runtime inspection** and **static source code analysis**, then exposes the results through REST APIs.

The goal of CoQuaAI is to help developers identify architectural issues, code smells, and common Spring Boot mistakes before they become maintenance problems.

---

# Architecture

CoQuaAI is divided into several independent modules.

```text
Spring Boot Application
            │
            ▼
  SpringContextScanner
            │
            ▼
     Runtime Metadata
            │
            ▼
   Runtime Rule Engine
            │
            ▼
   Static Rule Engine
            │
            ▼
      AI Review (Optional)
            │
            ▼
       REST Endpoints
```

Each component has a single responsibility, making the project easy to extend with additional rules.

---

# Runtime Analysis

The runtime analyzer starts after the Spring Boot application has been initialized.

It scans the Spring Application Context and discovers components such as:

* Controllers
* Services
* Repositories
* Components
* Configurations

Instead of parsing source files, this analysis works with actual Spring-managed beans.

For every discovered bean, CoQuaAI collects information such as:

* Bean type
* Package
* Dependencies
* Injected fields
* Public methods
* Implemented interfaces
* Parent classes

This metadata is then passed to the Runtime Rule Engine.

---

# Runtime Rule Engine

The Runtime Rule Engine executes rules that require knowledge of the running Spring application.

Examples include:

* Layer violations
* Circular dependencies
* Multiple controller responsibilities
* Package structure validation
* Dependency analysis

Every rule is independent and produces a report without affecting other rules.

This makes adding new runtime rules straightforward.

---

# Static Analysis

Some problems cannot be detected by inspecting Spring beans.

For these cases, CoQuaAI performs static source code analysis.

It scans Java source files and extracts information such as:

* Class declarations
* Fields
* Methods
* Annotations
* Imports
* Modifiers

This enables detection of issues before they appear at runtime.

---

# Static Rule Engine

The Static Rule Engine evaluates rules based on the parsed source code.

Examples include:

* Field Injection
* DTO Leakage
* Missing Request Mapping
* Naming Convention Violations
* Excessive Public Methods

Each rule returns:

* Rule name
* Severity
* Description
* Affected class
* Suggested improvement

---

# AI Review

CoQuaAI optionally integrates with Spring AI.

When configured, analysis results can be sent to an LLM to generate:

* Architecture feedback
* Refactoring suggestions
* Code quality summaries
* Best practice recommendations

AI integration is optional and requires users to configure their own AI provider and API credentials.

---

# REST API

After analysis completes, CoQuaAI exposes REST endpoints that return the generated reports.

Typical endpoints include:

* Runtime analysis
* Static analysis
* AI review
* Health information

This allows developers to integrate CoQuaAI with dashboards, CI/CD pipelines, or custom tools.

---

# Extending CoQuaAI

Adding a new rule is intentionally simple.

### Runtime Rule

1. Implement the runtime rule interface.
2. Read runtime metadata.
3. Return rule violations.
4. Register the rule.

### Static Rule

1. Implement the static rule interface.
2. Analyze parsed source information.
3. Return violations.
4. Register the rule.

No existing code needs to be modified, allowing new rules to be added without impacting existing functionality.

---

# Design Principles

CoQuaAI is designed around the following principles:

* Modular architecture
* Extensible rule engine
* Separation of runtime and static analysis
* Low coupling
* High cohesion
* Optional AI integration
* Easy integration into any Spring Boot project

---

# Why Build CoQuaAI?

Many code quality tools focus on syntax, formatting, or generic Java issues. CoQuaAI was created to provide analysis specifically for Spring Boot applications.

It combines:

* Spring runtime inspection
* Static source analysis
* Architecture validation
* Optional AI-powered reviews

into a single starter that developers can add to their projects with minimal configuration.

The project is designed to grow over time, making it easy to introduce new quality rules, architectural checks, and AI capabilities while keeping the core architecture clean and maintainable.
