package io.oipunk.neighbory.agent.impl;

import io.oipunk.neighbory.agent.core.Agent;
import io.oipunk.neighbory.agent.core.AgentContext;
import io.oipunk.neighbory.agent.core.AgentResult;
import io.oipunk.neighbory.common.LocaleMessageService;
import java.util.Locale;
import org.springframework.stereotype.Component;

/**
 * Billing advice agent (demo implementation).
 *
 * It currently returns rule-based guidance text. Future upgrades can:
 * - read outstanding payment data from storage
 * - integrate an LLM for richer explanations and wording
 */
@Component
public class BillingAdviceAgent implements Agent {

    private final LocaleMessageService messageService;

    public BillingAdviceAgent(LocaleMessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public String name() {
        return "billing";
    }

    @Override
    public boolean supports(AgentContext context) {
        if (context == null || context.text() == null) {
            return false;
        }
        String lower = context.text().toLowerCase(Locale.ROOT);
        return lower.contains("fee") || lower.contains("bill") || lower.contains("欠费") || lower.contains("缴费");
    }

    @Override
    public AgentResult run(AgentContext context) {
        return AgentResult.of(name(), messageService.get("agent.billing.advice"));
    }
}
