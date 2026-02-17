# Neighbory

`Neighbory` is a beginner-friendly Spring Boot project for neighborhood and property operations.
It is designed for developers who are new to Spring Boot and want a runnable, well-structured, and test-driven learning repository.

[![CI](https://github.com/OiPunk/neighbory/actions/workflows/ci.yml/badge.svg)](https://github.com/OiPunk/neighbory/actions/workflows/ci.yml)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://github.com/OiPunk/neighbory/blob/master/LICENSE)

## Why This Project

- Demonstrates modern Spring Boot engineering practices with clear boundaries.
- Keeps the code approachable for beginners without sacrificing quality gates.
- Uses deterministic local behavior (no external LLM dependency) while still showing multi-agent orchestration patterns.

## Tech Stack

- Java: 21
- Spring Boot: 3.5.10
- Springdoc OpenAPI: 2.8.6
- Flyway
- Spring Data JPA + Hibernate
- H2 (default) / MySQL profile
- Testing: JUnit 5 + Mockito + AssertJ + Spring Test
- Coverage: JaCoCo, enforced at 100% line coverage

## Architecture

- Layered monolith: `controller / service / repository / entity-dto / exception-common`
- Unified API envelope: `ApiResponse`
- Unified exception model: `ProblemDetail`
- Multi-agent demo endpoint: `POST /api/v1/agents/assist`
- Virtual threads enabled: `spring.threads.virtual.enabled=true`

## Project Structure

- `src/main/java/io/oipunk/neighbory`
- `src/main/resources`
- `src/test/java/io/oipunk/neighbory`
- `docs`

## Quick Start

Prerequisites:

- JDK 21+
- Git

Run the app:

```bash
./mvnw clean spring-boot:run
```

Useful endpoints:

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- API docs: `http://localhost:8080/api-docs`
- Health: `http://localhost:8080/actuator/health`

## Build, Test, and Coverage

```bash
./mvnw clean verify
```

`verify` enforces:

- all tests must pass
- total line coverage must remain 100%

## API Example

```bash
curl -H "Content-Type: application/json" \
  -d '{"text":"I want to pay but my bill looks wrong"}' \
  http://localhost:8080/api/v1/agents/assist
```

## Naming

- Product name: `Neighbory`
- Repository name: `neighbory`
- Maven project name: `neighbory`

## Open Source Collaboration

- Contribution guide: `CONTRIBUTING.md`
- Code of conduct: `CODE_OF_CONDUCT.md`
- Security policy: `SECURITY.md`
- PR/Issue templates: `.github/`

## Related Docs

- `docs/TECH_ARCHITECTURE.md`
- `docs/UPGRADE_NOTES.md`
- `docs/MULTI_AGENT_EXTENSION.md`
- `docs/MIDDLEWARE_VERSIONS.md`
- `docs/NAME_OPTIONS.md`
- `REPO_INTRODUCE.md`

## License

This project is released under the `MIT License`. See `LICENSE`.
