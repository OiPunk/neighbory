# Neighbory

`Neighbory` is a Spring Boot learning project for property/community operations.
It is designed for beginners who are new to Spring Boot and want a runnable, understandable, and extensible codebase.

[![CI](https://github.com/OiPunk/neighbory/actions/workflows/ci.yml/badge.svg)](https://github.com/OiPunk/neighbory/actions/workflows/ci.yml)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://github.com/OiPunk/neighbory/blob/master/LICENSE)

> Project background: the original repository referenced unavailable submodules (`family_service_platform` / `webproject`) and missed core source code. This project rebuilds the domain model based on the preserved `family_service_platform.sql` schema.

## Why This Refactor

- The old stack could not demonstrate modern Spring Boot features clearly.
- Dependency/version management was inconsistent.
- Internationalization was not systematically implemented.
- There was no measurable test coverage quality gate.

## Current Stack (Stable Latest at Upgrade Time)

- Java: 21
- Spring Boot: 3.5.10
- Springdoc OpenAPI: 2.8.6
- Testing: JUnit 5 + Mockito + AssertJ + Spring Test
- Coverage: JaCoCo (enforced 100% line coverage)
- Database migration: Flyway
- DB: H2 (default learning profile) / MySQL (`mysql` profile)
- Maven Wrapper: 3.9.6 (`./mvnw`)

## Architecture Upgrade Status

- Layered monolith completed: `controller / service / repository / entity-dto / exception-common-config`
- Core domain upgraded: Estate -> Building -> Unit aggregate relationship
- API governance upgraded: unified `ApiResponse` + `ProblemDetail`
- Observability upgraded: Actuator health/metrics endpoints
- Multi-agent skeleton implemented: `/api/v1/agents/assist` (fully local, no external LLM dependency)

## Framework and Middleware Versions

- Spring Framework: 6.2.15 (`spring-webmvc/spring-context`)
- Tomcat Embed: 10.1.50
- Hibernate ORM: 6.6.41.Final
- Spring Data JPA: 3.5.8
- Jackson: 2.19.4
- HikariCP: 6.3.3
- Hibernate Validator: 8.0.3.Final
- Flyway: 11.7.2
- Micrometer: 1.15.8
- MySQL Connector/J: 9.5.0
- H2: 2.3.232
- Swagger UI (WebJar): 5.20.1

## Project Structure

- `src/main/java/io/oipunk/neighbory`
- `src/main/resources`
- `src/test/java/io/oipunk/neighbory`
- `docs`

Core layers:

- `controller`: HTTP/API protocol layer
- `service`: domain/business orchestration layer
- `repository`: data access layer
- `entity/dto`: domain objects and API objects
- `exception`: unified exception output (`ProblemDetail`)
- `common/config`: shared response and i18n configuration

## Implemented Features

- Estate/Building/Unit core model
- RESTful CRUD examples (`/api/v1/estates`)
- i18n with `Accept-Language` negotiation and resource bundles
- Unified error model (`ProblemDetail`)
- Validation + localized validation/error messages
- OpenAPI docs: `/swagger-ui.html`
- Health check: `/actuator/health`
- Virtual threads enabled: `spring.threads.virtual.enabled=true`

## Quick Start

Prerequisites:

- JDK 21+
- Git
- Maven Wrapper is included in this repository

Check your Java version:

```bash
java -version
```

If the major version is not 21, switch to JDK 21 first.

macOS / Linux:

```bash
./mvnw clean spring-boot:run
```

Windows (PowerShell):

```powershell
.\mvnw.cmd clean spring-boot:run
```

Endpoints:

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- API Docs: `http://localhost:8080/api-docs`
- Health: `http://localhost:8080/actuator/health`

## Run Tests and Coverage

macOS / Linux:

```bash
./mvnw clean verify
```

Windows (PowerShell):

```powershell
.\mvnw.cmd clean verify
```

`verify` enforces JaCoCo checks:

- Total line coverage must be 100%
- Build fails if coverage drops below threshold

## I18n Examples

Chinese response:

```bash
curl -H 'Accept-Language: zh-CN' http://localhost:8080/api/v1/estates
```

English response:

```bash
curl -H 'Accept-Language: en-US' http://localhost:8080/api/v1/estates
```

## Project Naming

- Product name: `Neighbory`
- Repository name: `neighbory`
- Maven project name: `neighbory`

## Open Source Collaboration

- Contribution guide: `CONTRIBUTING.md`
- Code of conduct: `CODE_OF_CONDUCT.md`
- Security policy: `SECURITY.md`
- PR/Issue templates: `.github/`

Issues and pull requests are welcome.

## Suggested Next Learning Tasks

- Extend billing/workorder/parking domains from `family_service_platform.sql`
- Add richer migration strategies with Flyway
- Add MySQL integration tests with Testcontainers
- Evolve the multi-agent module toward Spring AI-based agents

See also:

- `docs/TECH_ARCHITECTURE.md`
- `docs/UPGRADE_AND_I18N.md`
- `docs/MULTI_AGENT_EXTENSION.md`
- `docs/MIDDLEWARE_VERSIONS.md`
- `docs/NAME_OPTIONS.md`
- `REPO_INTRODUCE.md`

Multi-agent demo endpoint:

- `POST /api/v1/agents/assist`

Example:

```bash
curl -H "Accept-Language: zh-CN" -H "Content-Type: application/json" \
  -d "{\"text\":\"I want to pay but my bill looks wrong\"}" \
  http://localhost:8080/api/v1/agents/assist
```

## License

This project is released under the `MIT License`. See `LICENSE`.
