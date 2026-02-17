package io.oipunk.neighbory.agent.impl;

import io.oipunk.neighbory.agent.core.Agent;
import io.oipunk.neighbory.agent.core.AgentContext;
import io.oipunk.neighbory.agent.core.AgentResult;
import io.oipunk.neighbory.common.MessageService;
import java.util.Locale;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * Minimal intent-classification agent (rule-based).
 *
 * Learning path: ship an end-to-end deterministic version first,
 * then replace it with an LLM-based classifier.
 */
@Component
public class KeywordIntentAgent implements Agent {

    private final MessageService messageService;

    public KeywordIntentAgent(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public String name() {
        return "intent";
    }

    @Override
    public boolean supports(AgentContext context) {
        return context != null && context.text() != null && !context.text().isBlank();
    }

    @Override
    public AgentResult run(AgentContext context) {
        String intent = classify(context.text());
        String summary = messageService.get("agent.intent.identified", intent);
        return AgentResult.of(name(), summary, Map.of("intent", intent));
    }

    private String classify(String text) {
        String lower = text.toLowerCase(Locale.ROOT);
        if (lower.contains("fee") || lower.contains("bill")) {
            return "BILLING";
        }
        if (lower.contains("repair") || lower.contains("fix")) {
            return "MAINTENANCE";
        }
        return "GENERAL";
    }
}
