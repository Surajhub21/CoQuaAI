# CoQuaAI 🚀

Context-Aware SOLID Principle Analyzer for Spring Boot Applications

---

# 📌 Overview

**CoQuaAI** is a static code analysis and architectural validation tool designed specifically for **Java and Spring Boot applications**.

Unlike traditional code analyzers, CoQuaAI understands:

- Spring Dependency Injection
- Bean Relationships
- Layered Architecture
- Context-Aware Dependencies
- Application Structure

The project focuses on detecting violations of **SOLID principles** using deep static analysis and Spring context awareness.

---

# ✨ Features

## ✅ SOLID Principle Detection

Analyze codebases for violations of:

- **S** → Single Responsibility Principle
- **O** → Open/Closed Principle
- **L** → Liskov Substitution Principle
- **I** → Interface Segregation Principle
- **D** → Dependency Inversion Principle

---

## ✅ Spring Context Awareness

CoQuaAI understands Spring Boot architecture and analyzes:

- `@Service`
- `@Repository`
- `@Controller`
- `@Component`
- Bean dependencies
- Constructor injection
- Circular dependencies
- Layer violations

---

## ✅ Static Code Analysis

Performs deep source-code analysis for:

- Class responsibilities
- Tight coupling
- Inheritance misuse
- Dependency chains
- Fat interfaces
- Package structure
- Architectural violations
- Service-layer complexity

---

## ✅ Maven Plugin Support

Run analysis directly from Maven:

```bash
mvn coquaai:analyze
```

---

## ✅ CI/CD Friendly

Can be integrated with:

- Maven Build Pipeline
- GitHub Actions
- Jenkins
- GitLab CI
- Sonar-based workflows

---

# 🏗️ Architecture

```text
coquaai/
│
├── core-engine/
├── parser-engine/
├── spring-context-engine/
├── rule-engine/
├── report-engine/
├── maven-plugin/
└── examples/
```

---

# 🛠️ Tech Stack

## Backend

- Java 21
- Spring Boot
- Maven

## Static Analysis

- JavaParser
- Spoon
- OpenRewrite
- ASM
- PMD Custom Rules

---

# 📖 How It Works

```text
Java Source Code
        ↓
AST Parsing
        ↓
Spring Context Resolution
        ↓
Dependency Graph Analysis
        ↓
SOLID Rule Evaluation
        ↓
Violation Detection
        ↓
Report Generation
```

---

# 📌 Example

## ❌ SRP Violation

```java
@Service
public class UserService {

    public void saveUser() {}

    public void sendEmail() {}

    public void generateReport() {}
}
```

### Detected Issues

- Multiple responsibilities detected
- Service layer overload
- Violates Single Responsibility Principle (SRP)

---

## ✅ Suggested Structure

```text
UserService
EmailService
ReportService
```

---

# 🎯 Project Goals

- Build a Spring-aware architecture analysis tool
- Improve maintainability of enterprise applications
- Detect architectural anti-patterns
- Reduce technical debt
- Enforce clean code principles automatically

---

# 🔮 Future Roadmap

## Planned Features

- Multi-module project analysis
- Incremental scanning
- Architecture rule configuration
- Custom rule engine
- Visual dependency graph
- IntelliJ Plugin
- VS Code Extension
- HTML reporting dashboard

---

# ⚡ Installation

## Clone Repository

```bash
git clone https://github.com/Surajhub21/CoQuaAI.git
```

## Build Project

```bash
./mvnw clean install
```

## Run Analyzer

```bash
mvn coquaai:analyze
```

---

# 🧪 Development

## Run Tests

```bash
./mvnw test
```

---

# 🤝 Contributing

Contributions are welcome.

You can contribute by:

- Adding new SOLID rules
- Improving Spring context analysis
- Enhancing architecture validation
- Optimizing parser performance
- Writing documentation

---

# 📜 License

MIT License

---

# 👨‍💻 Author

**Suraj Mondal**

GitHub: https://github.com/Surajhub21

Project: https://github.com/Surajhub21/CoQuaAI
