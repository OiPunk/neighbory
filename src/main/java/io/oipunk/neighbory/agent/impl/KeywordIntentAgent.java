package io.oipunk.neighbory.agent.impl;

import io.oipunk.neighbory.agent.core.Agent;
import io.oipunk.neighbory.agent.core.AgentContext;
import io.oipunk.neighbory.agent.core.AgentResult;
import io.oipunk.neighbory.common.LocaleMessageService;
import java.util.Locale;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * 一个最小可跑通的意图识别 Agent（规则版）。
 *
 * 教学点：先用确定性规则完成端到端，再替换成 LLM 分类器。
 */
@Component
public class KeywordIntentAgent implements Agent {

    private final LocaleMessageService messageService;

    public KeywordIntentAgent(LocaleMessageService messageService) {
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
        if (lower.contains("fee") || lower.contains("bill") || lower.contains("欠费") || lower.contains("缴费")) {
            return "BILLING";
        }
        if (lower.contains("repair") || lower.contains("fix") || lower.contains("报修") || lower.contains("故障")) {
            return "MAINTENANCE";
        }
        return "GENERAL";
    }
}
