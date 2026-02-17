package io.oipunk.neighbory.agent;

import io.oipunk.neighbory.agent.core.AgentContext;
import io.oipunk.neighbory.agent.core.AgentOrchestrator;
import io.oipunk.neighbory.agent.impl.BillingAdviceAgent;
import io.oipunk.neighbory.agent.impl.KeywordIntentAgent;
import io.oipunk.neighbory.agent.impl.MaintenanceAdviceAgent;
import io.oipunk.neighbory.common.MessageService;
import java.util.List;
import java.util.Locale;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.StaticMessageSource;

import static org.assertj.core.api.Assertions.assertThat;

class AgentOrchestratorTest {

    @Test
    void orchestratorShouldRunSupportedAgentsInStableOrder() {
        StaticMessageSource source = new StaticMessageSource();
        source.addMessage("agent.intent.identified", Locale.ENGLISH, "Intent: {0}");
        source.addMessage("agent.billing.advice", Locale.ENGLISH, "billing");
        source.addMessage("agent.maintenance.advice", Locale.ENGLISH, "maintenance");
        MessageService ms = new MessageService(source);

        var agents = List.of(
                new MaintenanceAdviceAgent(ms),
                new KeywordIntentAgent(ms),
                new BillingAdviceAgent(ms)
        );

        AgentOrchestrator orchestrator = new AgentOrchestrator(agents);
        var results = orchestrator.assist(new AgentContext("Please check my bill fee"));

        assertThat(results).extracting(r -> r.agent()).containsExactly("billing", "intent");
        assertThat(results.get(0).summary()).isEqualTo("billing");
        assertThat(results.get(1).data()).containsEntry("intent", "BILLING");
    }
}
