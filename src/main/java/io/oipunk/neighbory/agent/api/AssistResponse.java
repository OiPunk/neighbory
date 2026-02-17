package io.oipunk.neighbory.agent.api;

import io.oipunk.neighbory.agent.core.AgentResult;
import java.util.List;

/**
 * Multi-agent response payload.
 */
public record AssistResponse(List<AgentResult> steps) {
}
