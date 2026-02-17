# Multi-Agent Collaboration (Implemented Example)

This repository includes a local multi-agent collaboration skeleton that runs without any external LLM service.

## 1. Design Goals

- Pluggable: add a new agent by implementing an interface and registering a Spring Bean
- Testable: keep agent logic small and deterministic
- Evolvable: run with rule-based logic first, then upgrade to Spring AI / LLM agents

## 2. Core Abstractions

- `Agent`: one capability unit
- `AgentOrchestrator`: selects supported agents and executes them in deterministic order

Code location:

- `src/main/java/io/oipunk/neighbory/agent`

## 3. Included Agents

- `KeywordIntentAgent`: rule-based intent classification (`BILLING/MAINTENANCE/GENERAL`)
- `BillingAdviceAgent`: billing advice (demo)
- `MaintenanceAdviceAgent`: maintenance advice (demo)

## 4. API

- `POST /api/v1/agents/assist`

Request:

```json
{ "text": "I want to pay but my bill looks wrong" }
```

Response:

- `steps`: each agent output step for observability and learning

## 5. Suggested Next Evolution Tasks

1. Replace rule-based intent classification with a Spring AI classifier
2. Connect the billing agent to real outstanding-payment data
3. Let the maintenance agent create and persist work orders
4. Add parallel/voting orchestration strategies
