# Neighbory - Repository Introduction

`Neighbory` is a beginner-friendly Spring Boot project for property/community operations.
It is built to help learners move from "it runs" to "it is engineered".

## Positioning

- Learning-first: a complete path from basics to deliverable APIs
- Modern-first: demonstrates current Spring Boot practices
- Engineering-first: architecture, testing, and documentation quality are treated as first-class concerns

## Tech Stack

- Java 21
- Spring Boot 3.5.10
- Spring Data JPA + Hibernate
- Flyway
- Springdoc OpenAPI
- Spring Boot Actuator
- JUnit 5 + Mockito + AssertJ + JaCoCo

## Core Modules

- `estate`: estate, building, and unit core model
- `workorder`: work order creation and status flow
- `billing`: billing and payment lifecycle
- `parking`: parking allocation and query
- `agent`: local multi-agent orchestration skeleton

## Architecture Highlights

- Layered design: `controller -> service -> repository`
- Unified API protocol: `ApiResponse` + `ProblemDetail`
- Internationalization: `Accept-Language` + message bundles
- Observability: health and metrics endpoints
- Evolvable architecture: from rule-based orchestration to LLM-agent routing

## Quality Standards

- Unit/integration tests are part of normal development
- JaCoCo 100% line coverage quality gate
- Maven `verify` must pass
- Runtime health endpoint must return `UP`

## Target Audience

- Developers new to Spring Boot
- Developers moving from demos to production-style engineering habits
- Learners focusing on API design, testing discipline, i18n, and observability

## Suggested Learning Path

1. Read `README.md` and `docs/TECH_ARCHITECTURE.md`
2. Start the app and explore `swagger-ui`
3. Run tests and inspect JaCoCo reports
4. Extend one domain module or evolve the `agent` workflow
