# Neighbory

`Neighbory` 是一个基于 Spring Boot 的社区物业学习型项目，目标是给不会 Spring Boot 的新手提供一条可运行、可理解、可扩展的学习路径。

[![CI](https://github.com/OiPunk/neighbory/actions/workflows/ci.yml/badge.svg)](https://github.com/OiPunk/neighbory/actions/workflows/ci.yml)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://github.com/OiPunk/neighbory/blob/master/LICENSE)

> 现状说明：原仓库主代码目录是不可用的子模块引用（`family_service_platform` / `webproject`），公开仓库内缺失对应源码。本项目基于保留的 `family_service_platform.sql` 业务模型进行重建。

## 为什么重构

- 原项目技术栈时间较早，难以体现 Spring Boot 新特性
- 依赖与版本管理不统一，新手容易踩坑
- 缺少系统化国际化方案
- 缺少可量化测试覆盖率标准

## 当前技术栈（稳定最新版）

- Java: 21
- Spring Boot: 3.5.10
- Springdoc OpenAPI: 2.8.6
- 测试：JUnit 5 + Mockito + AssertJ + Spring Test
- 覆盖率：JaCoCo（强制 100% Line Coverage）
- 数据迁移：Flyway
- DB：H2（默认学习环境）/ MySQL（`mysql` profile）
- Maven Wrapper: 3.9.6（`./mvnw`）

## 架构升级现状

- 单体分层升级完成：`controller / service / repository / entity-dto / exception-common-config`
- 核心模型升级完成：Estate -> Building -> Unit 聚合关系
- 协议治理升级完成：统一 `ApiResponse` + `ProblemDetail`
- 可观测性升级完成：Actuator 健康检查与指标入口
- 多 Agent 骨架已落地：`/api/v1/agents/assist`（可本地跑通，不依赖外部 LLM）

## 框架与中间件实际版本（依赖树解析）

- Spring Framework: 6.2.15（`spring-webmvc/spring-context`）
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
- Swagger UI(WebJar): 5.20.1

## 项目结构

- `src/main/java/io/oipunk/neighbory`
- `src/main/resources`
- `src/test/java/io/oipunk/neighbory`
- `docs`

核心分层：

- `controller`: HTTP 协议层
- `service`: 领域业务层
- `repository`: 数据访问层
- `entity/dto`: 领域对象与接口对象
- `exception`: 统一异常（ProblemDetail）
- `common/config`: 通用响应与 i18n 配置

## 已落地能力

- Estate/Building/Unit 三层物业核心模型
- RESTful CRUD 示例（`/api/v1/estates`）
- 国际化（`Accept-Language` 自动协商，中英文资源包）
- 统一错误结构（ProblemDetail）
- 参数校验与本地化错误信息
- OpenAPI 文档：`/swagger-ui.html`
- Actuator 健康检查：`/actuator/health`
- 虚拟线程：`spring.threads.virtual.enabled=true`

## 快速启动

前置要求：

- JDK 21+
- Git
- 本仓库已包含 Maven Wrapper（无需本地单独安装 Maven）

检查 Java 版本：

```bash
java -version
```

如果输出主版本不是 21，请先切换到 JDK 21 再继续。

macOS / Linux：

```bash
./mvnw clean spring-boot:run
```

Windows (PowerShell)：

```powershell
.\mvnw.cmd clean spring-boot:run
```

访问：

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- API Docs: `http://localhost:8080/api-docs`
- Health: `http://localhost:8080/actuator/health`

## 运行测试与覆盖率

macOS / Linux：

```bash
./mvnw clean verify
```

Windows (PowerShell)：

```powershell
.\mvnw.cmd clean verify
```

`verify` 阶段会执行 JaCoCo 校验：

- 全项目 Line Coverage 必须为 100%
- 低于阈值会直接构建失败

## 国际化示例

中文：

```bash
curl -H 'Accept-Language: zh-CN' http://localhost:8080/api/v1/estates
```

英文：

```bash
curl -H 'Accept-Language: en-US' http://localhost:8080/api/v1/estates
```

## 项目命名

- 产品名：`Neighbory`
- 建议仓库名：`neighbory`
- Maven 工程名：`neighbory`

## 开源协作

- 贡献指南：`CONTRIBUTING.md`
- 社区行为准则：`CODE_OF_CONDUCT.md`
- 安全策略：`SECURITY.md`
- PR 模板与 Issue 模板：`.github/`

欢迎提交 Issue / PR，一起把这个 Spring Boot 练手项目做成长期可维护的开源模板。

## 后续可扩展（建议作为学习任务）

- 从 `family_service_platform.sql` 继续扩展收费、工单、车位管理等域
- 引入 Flyway 做数据库版本迁移
- 引入 Testcontainers 做 MySQL 真实集成测试
- 加入 Spring AI 多 Agent 协作模块（客服、运维、收费对账）

详见：

- `docs/TECH_ARCHITECTURE.md`
- `docs/UPGRADE_AND_I18N.md`
- `docs/MULTI_AGENT_EXTENSION.md`
- `docs/MIDDLEWARE_VERSIONS.md`
- `docs/NAME_OPTIONS.md`
- `REPO_INTRODUCE.md`

多 Agent 演示接口：

- `POST /api/v1/agents/assist`

示例：

```bash
curl -H "Accept-Language: zh-CN" -H "Content-Type: application/json" \
  -d "{\"text\":\"我要缴费但是账单不对\"}" \
  http://localhost:8080/api/v1/agents/assist
```

## 许可证

本项目基于 `MIT License` 开源，详见 `LICENSE`。
