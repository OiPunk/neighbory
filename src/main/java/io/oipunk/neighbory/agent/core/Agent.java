package io.oipunk.neighbory.agent.core;

/**
 * Agent 抽象：一个 Agent 做一件小事。
 *
 * 设计目标：
 * - 可插拔（新增 Agent 只需要实现接口并注册为 Spring Bean）
 * - 可测试（Agent 逻辑尽量纯函数化）
 * - 可编排（由 Orchestrator 决定调用顺序和策略）
 */
public interface Agent {

    /**
     * Agent 的唯一名称，用于输出可观测信息与调试。
     */
    String name();

    /**
     * 当前 Agent 是否适用于该请求上下文。
     */
    boolean supports(AgentContext context);

    /**
     * 执行并返回结果。
     */
    AgentResult run(AgentContext context);
}
