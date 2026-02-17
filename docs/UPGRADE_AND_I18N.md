# Upgrade and Internationalization Notes

## 1. Version Upgrade Highlights

- Spring Boot: `3.5.10`
- Java: `21`
- Springdoc OpenAPI: `2.8.6`
- Flyway: `11.7.2`
- Testing/coverage governance: JaCoCo + JUnit 5 + Mockito + AssertJ (100% line coverage)

## 2. Upgrade Benefits

- Uses modern stable versions to avoid outdated tutorial/API mismatch
- Standardizes dependency versions and reduces conflicts
- Enables modern capabilities out of the box (virtual threads, `ProblemDetail`)

## 3. i18n Design

- `AcceptHeaderLocaleResolver` for language negotiation
- `messages*.properties` bundles for multilingual resources
- localized business and exception text through the same message pipeline

## 4. Key Files

- `src/main/java/io/oipunk/neighbory/config/I18nConfig.java`
- `src/main/java/io/oipunk/neighbory/common/LocaleMessageService.java`
- `src/main/resources/messages_zh_CN.properties`
- `src/main/resources/messages_en_US.properties`

## 5. Request Examples

- `Accept-Language: zh-CN`
- `Accept-Language: en-US`
