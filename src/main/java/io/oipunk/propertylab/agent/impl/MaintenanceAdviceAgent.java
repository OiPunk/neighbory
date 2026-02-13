package io.oipunk.propertylab.agent.impl;

import io.oipunk.propertylab.agent.core.Agent;
import io.oipunk.propertylab.agent.core.AgentContext;
import io.oipunk.propertylab.agent.core.AgentResult;
import io.oipunk.propertylab.common.LocaleMessageService;
import java.util.Locale;
import org.springframework.stereotype.Component;

/**
 * 报修建议 Agent（演示版）。
 */
@Component
public class MaintenanceAdviceAgent implements Agent {

    private final LocaleMessageService messageService;

    public MaintenanceAdviceAgent(LocaleMessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public String name() {
        return "maintenance";
    }

    @Override
    public boolean supports(AgentContext context) {
        if (context == null || context.text() == null) {
            return false;
        }
        String lower = context.text().toLowerCase(Locale.ROOT);
        return lower.contains("repair") || lower.contains("fix") || lower.contains("报修") || lower.contains("故障");
    }

    @Override
    public AgentResult run(AgentContext context) {
        return AgentResult.of(name(), messageService.get("agent.maintenance.advice"));
    }
}
