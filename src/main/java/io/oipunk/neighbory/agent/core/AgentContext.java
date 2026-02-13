package io.oipunk.neighbory.agent.core;

import java.util.Locale;

/**
 * Agent 上下文：承载输入与协作状态。
 *
 * 这里保持轻量化，新手更容易理解。更复杂场景可扩展：用户信息、会话、工具、数据库连接等。
 */
public record AgentContext(String text, Locale locale) {
}
