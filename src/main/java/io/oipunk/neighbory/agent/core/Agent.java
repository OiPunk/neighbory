package io.oipunk.neighbory.agent.core;

/**
 * Agent abstraction: one agent should do one focused job.
 *
 * Design goals:
 * - pluggable: add an agent by implementing this interface and registering a Spring Bean
 * - testable: keep logic as pure and deterministic as possible
 * - orchestratable: execution order and strategy are controlled by the orchestrator
 */
public interface Agent {

    /**
     * Unique agent name for observability and debugging.
     */
    String name();

    /**
     * Whether this agent applies to the current request context.
     */
    boolean supports(AgentContext context);

    /**
     * Execute the agent and return its result.
     */
    AgentResult run(AgentContext context);
}
