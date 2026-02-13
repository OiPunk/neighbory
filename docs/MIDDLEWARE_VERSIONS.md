# 框架与中间件版本清单（2026-02-13）

来源：`./mvnw dependency:tree` 实际解析结果（非手填）。

## 核心框架

- Spring Boot: `3.5.10`
- Spring Framework: `6.2.15`
- Spring Data JPA: `3.5.8`

## Web 与序列化

- Tomcat Embed: `10.1.50`
- Jackson: `2.19.4`
- springdoc-openapi-starter-webmvc-ui: `2.8.6`
- Swagger UI (WebJar): `5.20.1`

## 数据与校验

- Hibernate ORM: `6.6.41.Final`
- Hibernate Validator: `8.0.3.Final`
- HikariCP: `6.3.3`
- Flyway Core: `11.7.2`
- MySQL Connector/J: `9.5.0`
- H2: `2.3.232`

## 可观测性

- Micrometer: `1.15.8`

## 测试

- JUnit Jupiter: `5.12.2`
- Mockito: `5.17.0`
- AssertJ: `3.27.6`
- JaCoCo Plugin: `0.8.14`

## 构建链路

- Maven Wrapper distribution: `3.9.6`
- Java version enforced by Enforcer: `>= 21`
