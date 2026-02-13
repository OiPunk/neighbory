package io.oipunk.propertylab.agent.impl;

import io.oipunk.propertylab.agent.core.Agent;
import io.oipunk.propertylab.agent.core.AgentContext;
import io.oipunk.propertylab.agent.core.AgentResult;
import io.oipunk.propertylab.common.LocaleMessageService;
import java.util.Locale;
import org.springframework.stereotype.Component;

/**
 * 账单建议 Agent（演示版）。
 *
 * 当前用规则输出一段建议文本；后续可以：
 * - 读取数据库欠费数据
 * - 接入 LLM 生成解释与话术
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
