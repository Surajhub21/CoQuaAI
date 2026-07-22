# CoQuaAI

> **A lightweight Spring Boot Starter for Runtime Analysis, Static Code Analysis, and AI-powered Code Quality Reviews.**

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.x-brightgreen)
![License](https://img.shields.io/badge/License-MIT-blue)
![Status](https://img.shields.io/badge/Status-Active-success)

---

# 🎥 Project Demo

Watch CoQuaAI in action:

<iframe width="560" height="315" src="https://app.heygen.com/embeds/eb7fc82f2bed4222a28b62dc4d8e20fb" title="CoQuaAI Demo" frameborder="0" allow="encrypted-media; fullscreen;" allowfullscreen></iframe>

> **Note:** If the video does not render on GitHub, open it directly from the repository page or provide the HeyGen share link.

---

# 🚀 At a Glance

CoQuaAI is a **Spring Boot Starter** that automatically analyzes your Spring Boot application after startup.

It combines:

- ✅ Runtime Spring Bean Analysis
- ✅ Static Java Source Analysis
- ✅ Architecture Validation
- ✅ Spring Best Practice Checks
- ✅ AI-powered Code Review (Optional)
- ✅ REST APIs for accessing reports

Simply add the dependency, start your application, and CoQuaAI begins analyzing your project.

---

# ✨ Features

- Runtime Analysis of Spring Components
- Static Java Source Code Analysis
- Extensible Rule Engine
- Architecture Validation
- Dependency Analysis
- Circular Dependency Detection
- DTO Leakage Detection
- Field Injection Detection
- Naming Convention Validation
- Missing Request Mapping Detection
- Optional Spring AI Integration
- REST APIs
- Easy to Extend with Custom Rules

---

# ⚡ Quick Start

## 1. Add the Dependency

```xml
<dependency>
    <groupId>com.github.Surajhub21</groupId>
    <artifactId>developersuraj-coquaai</artifactId>
    <version>1.0.0</version>
</dependency>
```

> Since the project is published through **GitHub Packages**, configure the GitHub Maven repository before adding the dependency.

---

## 2. Start Your Application

Simply run your Spring Boot application.

CoQuaAI automatically:

- Scans Spring Beans
- Collects Runtime Metadata
- Parses Java Source Code
- Executes Runtime Rules
- Executes Static Rules
- Generates Reports

No manual scanning is required.

---

## 3. Access Reports

Typical endpoints include:

```
/coqua/runtime
/coqua/static
/coqua/ai-review
/coqua/health
```

---

# 🏗️ Architecture

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

Each module has a single responsibility, making the framework modular, maintainable, and easy to extend.

---

# 🔍 Runtime Analysis

After Spring Boot finishes starting, CoQuaAI scans the **Spring Application Context**.

It discovers:

- Controllers
- Services
- Repositories
- Components
- Configurations

For every Spring-managed bean, CoQuaAI collects:

- Bean Type
- Package Name
- Dependencies
- Injected Fields
- Public Methods
- Interfaces
- Parent Classes

The collected metadata is passed to the Runtime Rule Engine.

---

# ⚙️ Runtime Rule Engine

Runtime rules inspect the actual running Spring application.

Examples include:

- Layer Violations
- Circular Dependencies
- Multiple Controller Responsibilities
- Package Structure Validation
- Dependency Analysis

Every rule is independent, allowing new runtime rules to be added without modifying existing code.

---

# 📄 Static Analysis

Some issues cannot be detected from runtime metadata alone.

CoQuaAI parses Java source files using static analysis to inspect:

- Classes
- Fields
- Methods
- Imports
- Annotations
- Modifiers

This enables early detection of common code quality issues.

---

# 📋 Static Rule Engine

The Static Rule Engine evaluates parsed source information.

Current rule examples include:

- Field Injection
- DTO Leakage
- Missing Request Mapping
- Naming Convention Validation
- Excessive Public Methods

Each violation contains:

- Rule Name
- Severity
- Description
- Affected Class
- Suggested Improvement

---

# 🤖 AI Review (Optional)

CoQuaAI integrates with **Spring AI**.

When configured with your preferred AI provider, CoQuaAI can generate:

- Architecture Feedback
- Refactoring Suggestions
- Code Quality Summaries
- Spring Boot Best Practice Recommendations

AI integration is optional.

Users configure their own API keys and AI provider.

---

# 🌐 REST API

After analysis completes, CoQuaAI exposes REST endpoints that return generated reports.

Examples:

- Runtime Analysis
- Static Analysis
- AI Review
- Health Status

These APIs can easily integrate with:

- CI/CD Pipelines
- Internal Dashboards
- Developer Portals
- Automation Scripts

---

# 📈 Performance

CoQuaAI is designed to have minimal runtime overhead.

- Analysis runs once during application startup.
- Runtime metadata is collected only once.
- Results are cached and exposed through REST APIs.
- No continuous background analysis is performed unless explicitly configured.

---

# 🧩 Extending CoQuaAI

Adding new rules is intentionally simple.

## Runtime Rule

1. Implement the Runtime Rule interface.
2. Read Runtime Metadata.
3. Return Rule Violations.
4. Register the Rule.

## Static Rule

1. Implement the Static Rule interface.
2. Analyze Parsed Source Information.
3. Return Rule Violations.
4. Register the Rule.

No existing code needs to be modified.

---

# 🎯 Design Principles

CoQuaAI follows:

- Modular Architecture
- Single Responsibility Principle
- Open/Closed Principle
- High Cohesion
- Low Coupling
- Extensible Rule Engine
- Runtime & Static Separation
- Optional AI Integration

---

# 🛣️ Roadmap

Upcoming features include:

- SOLID Principle Analyzer
- Design Pattern Detection
- Dependency Graph Visualization
- HTML Report Generation
- GitHub Action Integration
- SonarQube Plugin
- Architecture Scoring
- Custom Rule SDK
- Multi-module Project Support

---

# ❓ Why CoQuaAI?

Most code quality tools focus on generic Java issues.

CoQuaAI was built specifically for **Spring Boot applications**.

It combines:

- Spring Runtime Inspection
- Static Source Analysis
- Architecture Validation
- AI-powered Reviews

into a single lightweight Spring Boot Starter.

The architecture is intentionally modular so that new quality rules and analysis engines can be added with minimal effort.

---

# ⭐ If you like this project

If CoQuaAI helped you or inspired you, consider giving the repository a ⭐ on GitHub.

It helps others discover the project and motivates future development.
