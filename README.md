# CoQuaAI 🚀

AI-Powered SOLID Principle Analyzer for Java & Spring Boot Applications

---

## 📌 Overview

**CoQuaAI** is an AI-powered code quality and architecture analysis platform designed to detect violations of **SOLID principles** in Java/Spring Boot applications.

The project combines:

- Static Code Analysis
- AST Parsing
- Rule-Based Architecture Validation
- AI-Generated Explanations
- Refactoring Suggestions

Its goal is to help developers build cleaner, scalable, maintainable, and enterprise-grade software.

---

# ✨ Features

## ✅ SOLID Principle Detection

Analyze source code for violations of:

- **S** → Single Responsibility Principle
- **O** → Open/Closed Principle
- **L** → Liskov Substitution Principle
- **I** → Interface Segregation Principle
- **D** → Dependency Inversion Principle

---

## ✅ AI-Powered Suggestions

Generate:

- Refactoring suggestions
- Cleaner implementations
- Architecture improvements
- Best-practice explanations

---

## ✅ Static Code Analysis

Analyze:

- Classes
- Interfaces
- Dependencies
- Inheritance
- Method complexity
- Coupling
- Package structure

---

## ✅ Maven Plugin Support

Run analysis directly from Maven:

```bash
mvn coquaai:analyze
```

---

## ✅ Spring Boot Integration

Can be integrated as:

- Maven Plugin
- Spring Boot Starter
- CI/CD Quality Gate
- GitHub Action

---

# 🏗️ Planned Architecture

```text
coquaai/
│
├── core-engine/
├── rule-engine/
├── parser-module/
├── ai-module/
├── report-module/
├── maven-plugin/
├── spring-boot-starter/
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
- PMD Custom Rules

## AI Layer

- OpenAI API
- LangChain4j
- LangGraph (Future)

## Reporting

- HTML Reports
- JSON Reports
- CI/CD Integration

---

# 📖 Workflow

```text
Java Source Code
        ↓
AST Parsing
        ↓
Rule Engine Analysis
        ↓
Violation Detection
        ↓
AI Explanation Layer
        ↓
Refactoring Suggestions
        ↓
Report Generation
```

---

# 📌 Example

## ❌ SRP Violation

```java
public class UserService {

    public void saveUser() {}

    public void sendEmail() {}

    public void generateReport() {}
}
```

### Detected Problem

- Multiple responsibilities detected
- Violates Single Responsibility Principle (SRP)

---

## ✅ Suggested Refactor

```java
UserService
EmailService
ReportService
```

---

# 🎯 Project Goals

- Build a developer-friendly architecture analysis tool
- Improve code maintainability
- Reduce technical debt
- Provide AI-assisted refactoring
- Create enterprise-grade quality gates

---

# 🔮 Future Roadmap

## Planned Features

- Auto-refactoring engine
- UML generation
- Design pattern detection
- Architecture visualization
- Multi-language support
- GitHub PR review bot
- VS Code Plugin
- IntelliJ Plugin

---

# ⚡ Installation

## Clone Repository

```bash
git clone https://github.com/Surajhub21/CoQuaAI.git
```

## Run Application

```bash
cd CoQuaAI
./mvnw spring-boot:run
```

---

# 🧪 Development

## Run Tests

```bash
./mvnw test
```

## Build Project

```bash
./mvnw clean install
```

---

# 🤝 Contributing

Contributions are welcome.

You can contribute by:

- Adding new SOLID rules
- Improving AI prompts
- Enhancing report generation
- Optimizing analysis performance
- Writing documentation

---

# 📜 License

MIT License

---

# 👨‍💻 Author

**Suraj Mondal**

GitHub: https://github.com/Surajhub21

Project: https://github.com/Surajhub21/CoQuaAI
