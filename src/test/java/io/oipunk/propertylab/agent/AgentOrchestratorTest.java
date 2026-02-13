package io.oipunk.propertylab.agent;

import io.oipunk.propertylab.agent.core.AgentContext;
import io.oipunk.propertylab.agent.core.AgentOrchestrator;
import io.oipunk.propertylab.agent.impl.BillingAdviceAgent;
import io.oipunk.propertylab.agent.impl.KeywordIntentAgent;
import io.oipunk.propertylab.agent.impl.MaintenanceAdviceAgent;
import io.oipunk.propertylab.common.LocaleMessageService;
import java.util.List;
import java.util.Locale;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.StaticMessageSource;

import static org.assertj.core.api.Assertions.assertThat;

class AgentOrchestratorTest {

    @AfterEach
    void cleanup() {
        LocaleContextHolder.resetLocaleContext();
    }

    @Test
    void orchestratorShouldRunSupportedAgentsInStableOrder() {
        StaticMessageSource source = new StaticMessageSource();
        source.addMessage("agent.intent.identified", Locale.US, "Intent: {0}");
        source.addMessage("agent.billing.advice", Locale.US, "billing");
        source.addMessage("agent.maintenance.advice", Locale.US, "maintenance");
        LocaleMessageService ms = new LocaleMessageService(source);
        LocaleContextHolder.setLocale(Locale.US);

        var agents = List.of(
                new MaintenanceAdviceAgent(ms),
                new KeywordIntentAgent(ms),
                new BillingAdviceAgent(ms)
        );

        AgentOrchestrator orchestrator = new AgentOrchestrator(agents);
        var results = orchestrator.assist(new AgentContext("Please check my bill fee", Locale.US));

        assertThat(results).extracting(r -> r.agent()).containsExactly("billing", "intent");
        assertThat(results.get(0).summary()).isEqualTo("billing");
        assertThat(results.get(1).data()).containsEntry("intent", "BILLING");
    }
}
