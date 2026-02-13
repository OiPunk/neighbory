package io.oipunk.propertylab.agent.api;

import io.oipunk.propertylab.agent.core.AgentResult;
import java.util.List;

/**
 * 多 Agent 协作输出。
 */
public record AssistResponse(List<AgentResult> steps) {
}
