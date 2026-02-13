# Neighbory - Repo Introduce

`Neighbory` 是一个面向 Spring Boot 新手的社区物业管理学习项目。  
目标是让你在一个可运行、可测试、可扩展的代码库中，系统掌握 Spring Boot 3.5 的现代开发方式。

## 项目定位

- 教学优先：从零基础到可交付 API 的完整路径
- 现代优先：采用 Spring Boot 最新稳定版本特性
- 工程优先：强调分层架构、测试质量、文档完整性

## 技术栈

- Java 21
- Spring Boot 3.5.10
- Spring Data JPA + Hibernate
- Flyway
- Springdoc OpenAPI
- Spring Boot Actuator
- JUnit 5 + Mockito + AssertJ + JaCoCo

## 核心模块

- `estate`：小区、楼栋、单元等核心模型
- `workorder`：工单创建与状态流转
- `billing`：账单与费用管理
- `parking`：车位分配与查询
- `agent`：多 Agent 协作骨架（可本地跑通）

## 架构特点

- 经典分层：`controller -> service -> repository`
- 统一协议：`ApiResponse` + `ProblemDetail`
- 国际化：`Accept-Language` + message bundle
- 可观测性：健康检查、指标暴露
- 可演进：支持从规则引擎扩展到 LLM Agent

## 质量标准

- 单元测试与集成测试齐备
- JaCoCo 100% 行覆盖率门禁
- Maven `verify` 必须通过
- 启动后 `actuator/health` 必须为 `UP`

## 适合谁

- 刚接触 Spring Boot 的初学者
- 想从“能跑”走向“工程化”的进阶开发者
- 希望学习 API 设计、测试治理、国际化与可观测性的同学

## 建议学习路径

1. 先阅读 `README.md` 与 `docs/TECH_ARCHITECTURE.md`
2. 启动项目并浏览 `swagger-ui`
3. 跑通测试并查看 JaCoCo 报告
4. 逐步扩展 `agent` 或某个业务域能力
