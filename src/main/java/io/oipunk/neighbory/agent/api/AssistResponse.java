package io.oipunk.neighbory.agent.api;

import io.oipunk.neighbory.agent.core.AgentResult;
import java.util.List;

/**
 * 多 Agent 协作输出。
 */
public record AssistResponse(List<AgentResult> steps) {
}
