package io.oipunk.neighbory.agent.impl;

import io.oipunk.neighbory.agent.core.Agent;
import io.oipunk.neighbory.agent.core.AgentContext;
import io.oipunk.neighbory.agent.core.AgentResult;
import io.oipunk.neighbory.common.MessageService;
import java.util.Locale;
import org.springframework.stereotype.Component;

/**
 * Maintenance advice agent (demo implementation).
 */
@Component
public class MaintenanceAdviceAgent implements Agent {

    private final MessageService messageService;

    public MaintenanceAdviceAgent(MessageService messageService) {
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
        return lower.contains("repair") || lower.contains("fix");
    }

    @Override
    public AgentResult run(AgentContext context) {
        return AgentResult.of(name(), messageService.get("agent.maintenance.advice"));
    }
}
