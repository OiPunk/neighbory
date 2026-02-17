package io.oipunk.neighbory.agent.core;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Multi-agent orchestrator:
 * - select agents where `supports(context)` is true
 * - execute agents in deterministic order
 *
 * Learning path: start with deterministic orchestration and then evolve toward
 * advanced strategies (parallel execution, voting, rollback, LLM-based routing).
 */
@Service
public class AgentOrchestrator {

    private final List<Agent> agents;

    public AgentOrchestrator(List<Agent> agents) {
        // Keep execution order stable so results do not vary by bean injection order.
        this.agents = agents.stream().sorted(Comparator.comparing(Agent::name)).toList();
    }

    public List<AgentResult> assist(AgentContext context) {
        List<AgentResult> results = new ArrayList<>();
        for (Agent agent : agents) {
            if (!agent.supports(context)) {
                continue;
            }
            results.add(agent.run(context));
        }
        return results;
    }
}
