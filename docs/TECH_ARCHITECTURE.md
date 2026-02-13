# 技术架构梳理

## 1. 目标

该项目定位为 Spring Boot 新手学习工程，强调：

- 分层架构清晰
- 可运行、可调试
- 能覆盖主流工程实践（异常治理、国际化、测试、文档）

## 2. 分层结构（按子域组织）

- 主子域：`estate`, `billing`, `workorder`, `parking`
- API 层：`*/controller`
- 业务层：`*/service`
- 持久层：`*/repository`
- 模型层：`*/entity`, `*/dto`
- 智能体层：`agent/api`, `agent/core`, `agent/impl`
- 横切层：`exception`, `common`, `config`

## 3. 数据模型（当前阶段）

基于原 SQL 的核心主链路进行映射：

- `fc_estate` -> `estates`
- `fc_building` -> `buildings`
- `fc_unit` -> `units`
- `billing_charges`（收费单）
- `work_orders`（工单）
- `parking_spaces`（车位）

关系：

- Estate 1:N Building
- Building 1:N Unit

## 4. 新版 Spring Boot 特性实践

- `ProblemDetail` 统一异常
- `spring.threads.virtual.enabled=true` 启用虚拟线程
- `Actuator` 可观测性入口
- `springdoc-openapi` 自动文档
- `Flyway` 数据库版本迁移（`V1~V4`）
- `Maven Enforcer + Maven Wrapper` 固化构建环境（Java 21 / Maven 3.9.x）
- `JaCoCo 100% Line Coverage` 作为质量闸门
- `Multi-Agent Orchestrator`（规则版）演示可插拔智能体协作骨架

## 5. 当前可用新特性（可直接学习）

1. 统一异常模型：Controller 不再手写散乱错误响应，交给全局处理器返回 `ProblemDetail`
2. 国际化链路：`Accept-Language -> MessageSource -> 业务/校验/异常文案`
3. 虚拟线程：开启后 Web 请求可用轻量线程模型（适合 I/O 密集场景）
4. 依赖治理：Spring Boot BOM + Enforcer + Wrapper，减少“本地环境不一致”问题
5. 可观测入口：健康检查与指标端点可直接接监控系统
6. 多 Agent 编排：支持按 `supports()` 规则路由到不同 Agent，并稳定顺序执行

## 6. 可扩展架构建议

后续按领域继续拆分包（如 `billing`, `workorder`, `parking`），并保持：

- Controller 只做协议转换
- Service 只做业务编排
- Repository 只做数据访问
