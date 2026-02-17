# Technical Architecture

## 1. Goal

This repository is a Spring Boot learning project focused on:

- clear layered architecture
- runnable and debuggable implementation
- practical engineering practices (exception governance, testing, and documentation)

## 2. Layering by Domain

- Main domains: `estate`, `billing`, `workorder`, `parking`
- API layer: `*/controller`
- Business layer: `*/service`
- Persistence layer: `*/repository`
- Model layer: `*/entity`, `*/dto`
- Agent layer: `agent/api`, `agent/core`, `agent/impl`
- Cross-cutting layer: `exception`, `common`

## 3. Data Model (Current Scope)

Mapped from the baseline domain model:

- `estates`
- `buildings`
- `units`
- `billing_charges`
- `work_orders`
- `parking_spaces`

Relationships:

- Estate 1:N Building
- Building 1:N Unit

## 4. Modern Spring Boot Practices Used

- unified exception output with `ProblemDetail`
- virtual threads with `spring.threads.virtual.enabled=true`
- observability via `Actuator`
- API documentation via `springdoc-openapi`
- schema versioning via Flyway (`V1`~`V4`)
- build consistency via Maven Enforcer + Maven Wrapper (Java 21 / Maven 3.9.x)
- JaCoCo 100% line coverage as a quality gate
- rule-based `Multi-Agent Orchestrator` to demonstrate pluggable collaboration

## 5. Learning-Oriented Features

1. Unified error model: controllers avoid ad-hoc error shapes and delegate to global handling.
2. Centralized response messages via `messages.properties`.
3. Virtual threads: lightweight request concurrency model for I/O-bound workloads.
4. Dependency governance: BOM + Enforcer + Wrapper for stable local/CI behavior.
5. Observability entry points: health and metrics endpoints ready for monitoring.
6. Agent orchestration: route by `supports()` and run in deterministic order.

## 6. Extension Guidance

As domains grow, keep these boundaries:

- Controller handles protocol mapping only
- Service handles business orchestration only
- Repository handles data access only
