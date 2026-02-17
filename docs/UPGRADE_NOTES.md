# Upgrade Notes

## 1. Version Upgrade Highlights

- Spring Boot: `3.5.10`
- Java: `21`
- Springdoc OpenAPI: `2.8.6`
- Flyway: `11.7.2`
- Testing/coverage governance: JaCoCo + JUnit 5 + Mockito + AssertJ (100% line coverage)

## 2. Upgrade Benefits

- Uses modern stable versions to avoid outdated tutorial/API mismatch.
- Standardizes dependency versions and reduces conflicts.
- Enables modern capabilities out of the box (`ProblemDetail`, virtual threads, stronger validation defaults).

## 3. Message Strategy

- Single English resource bundle (`messages.properties`) for stable learning behavior.
- Business, validation, and exception messages are resolved through one service (`MessageService`).
- No locale negotiation is required in this project.

## 4. Key Files

- `pom.xml`
- `src/main/java/io/oipunk/neighbory/common/MessageService.java`
- `src/main/resources/messages.properties`
- `src/main/java/io/oipunk/neighbory/exception/GlobalExceptionHandler.java`
