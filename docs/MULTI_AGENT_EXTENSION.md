# 多 Agent 协作（已落地示例）

本项目内置一个不依赖外部 LLM 的多 Agent 协作骨架，保证新手可以本地跑通并理解。

## 1. 设计目标

- 可插拔：新增 Agent 只需要实现接口并注册为 Spring Bean
- 可测试：Agent 逻辑尽量小、纯、可测
- 可演进：规则版先跑通，再替换成 Spring AI / LLM Agent

## 2. 核心抽象

- `Agent`：单个能力单元
- `AgentOrchestrator`：编排器（选择 supports 的 Agent 并按稳定顺序执行）

代码位置：

- `src/main/java/io/oipunk/neighbory/agent`

## 3. 已包含 Agent

- `KeywordIntentAgent`：规则版意图识别（BILLING/MAINTENANCE/GENERAL）
- `BillingAdviceAgent`：账单建议（演示版）
- `MaintenanceAdviceAgent`：报修建议（演示版）

## 4. API

- `POST /api/v1/agents/assist`

请求：

```json
{ "text": "我要缴费但是账单不对" }
```

响应：

- `steps`: 每个 Agent 的输出步骤，便于理解多 Agent 协作过程

## 5. 下一步演进（建议作为练习）

1. 把规则意图识别替换为 Spring AI 分类器
2. 账单 Agent 接入数据库查询欠费数据
3. 报修 Agent 创建工单实体并持久化
4. Orchestrator 支持并行与投票策略
