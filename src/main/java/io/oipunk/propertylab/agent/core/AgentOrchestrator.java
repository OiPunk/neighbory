package io.oipunk.propertylab.agent.core;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * 多 Agent 编排器：
 * - 选择 supports 的 Agent
 * - 按固定顺序执行
 *
 * 教学点：先从确定性流程开始，再逐步演进为更复杂的策略（并行、投票、回溯、LLM Router）。
 */
@Service
public class AgentOrchestrator {

    private final List<Agent> agents;

    public AgentOrchestrator(List<Agent> agents) {
        // 保证稳定顺序，避免 Bean 注入顺序导致结果波动
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
