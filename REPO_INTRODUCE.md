# Neighbory - Repository Introduction

`Neighbory` is a beginner-friendly Spring Boot project for neighborhood and property operations.
It helps learners move from "it runs" to "it is engineered".

## Positioning

- Learning-first: complete path from starter APIs to maintainable service code
- Modern-first: current Spring Boot and Java versions
- Engineering-first: architecture, tests, and documentation quality are treated as first-class concerns

## Tech Stack

- Java 21
- Spring Boot 3.5.10
- Spring Data JPA + Hibernate
- Flyway
- Springdoc OpenAPI
- Spring Boot Actuator
- JUnit 5 + Mockito + AssertJ + JaCoCo

## Core Modules

- `estate`: estate, building, and unit model
- `workorder`: ticket creation and status flow
- `billing`: charge creation and payment lifecycle
- `parking`: parking space assignment and release
- `agent`: local multi-agent orchestration skeleton

## Architecture Highlights

- Layered design: `controller -> service -> repository`
- Unified API protocol: `ApiResponse` + `ProblemDetail`
- English-only message resources for consistent responses
- Observability: health and metrics endpoints
- Evolvable architecture: from rule-based orchestration to LLM-agent routing

## Quality Standards

- Unit/integration tests are mandatory for behavior changes
- JaCoCo 100% line coverage quality gate
- Maven `verify` must pass
- Runtime health endpoint must return `UP`

## Target Audience

- Developers new to Spring Boot
- Developers moving from demos to production-style engineering habits
- Learners focused on API design, testing discipline, and observability

## Suggested Learning Path

1. Read `README.md` and `docs/TECH_ARCHITECTURE.md`
2. Start the app and explore `swagger-ui`
3. Run tests and inspect JaCoCo reports
4. Extend one domain module or evolve the `agent` workflow
