package io.oipunk.neighbory.agent.core;

import java.util.Map;

/**
 * Execution result produced by a single agent.
 */
public record AgentResult(String agent, String summary, Map<String, Object> data) {

    public static AgentResult of(String agent, String summary) {
        return new AgentResult(agent, summary, Map.of());
    }

    public static AgentResult of(String agent, String summary, Map<String, Object> data) {
        return new AgentResult(agent, summary, data);
    }
}
