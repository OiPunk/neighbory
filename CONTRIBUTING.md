# Contributing to Neighbory

Thank you for contributing to `Neighbory`.

## Project Intent

This project is both:

- a beginner-friendly Spring Boot learning codebase
- an open-source repository maintained with engineering quality standards

Please make sure your changes are:

- understandable for beginners
- behavior-safe unless a breaking change is explicitly documented
- verified with `./mvnw clean verify` (or `.\\mvnw.cmd clean verify` on Windows)
- compliant with the JaCoCo quality gate (100% line coverage)

## Development Environment

- JDK 21+
- Git
- Maven Wrapper (already included)

## Contribution Workflow

1. Fork and create a branch (`feat/*`, `fix/*`, `docs/*`, etc.)
2. Implement code and tests
3. Run local verification:
   - macOS/Linux: `./mvnw clean verify`
   - Windows: `.\\mvnw.cmd clean verify`
4. Update related documentation (`README` / `docs`)
5. Open a Pull Request

## Commit Message Convention

Conventional Commits are recommended:

- `feat:` new feature
- `fix:` bug fix
- `docs:` documentation update
- `refactor:` refactor without behavior change
- `test:` test changes
- `chore:` build/dependency/tooling change

Examples:

- `feat(estate): add estate query by manager id`
- `fix(i18n): fallback to default locale when header is invalid`

## Pull Request Checklist

Include at least:

- change purpose
- key implementation points
- validation commands and result summary
- breaking change impact (if any)
- documentation updates (if behavior changed)

## Coding and Documentation Style

- Keep layer boundaries clear: `controller -> service -> repository`
- Avoid putting domain logic in controllers
- Add tests for behavior changes
- Add short comments where logic is non-obvious

## Security Reporting

Do not report vulnerabilities in public issues.
Please follow `SECURITY.md`.
