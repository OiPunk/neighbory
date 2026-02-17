package io.oipunk.neighbory.agent;

import io.oipunk.neighbory.agent.core.AgentContext;
import io.oipunk.neighbory.agent.core.AgentResult;
import io.oipunk.neighbory.agent.impl.BillingAdviceAgent;
import io.oipunk.neighbory.agent.impl.KeywordIntentAgent;
import io.oipunk.neighbory.agent.impl.MaintenanceAdviceAgent;
import io.oipunk.neighbory.common.MessageService;
import java.util.Locale;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.StaticMessageSource;

import static org.assertj.core.api.Assertions.assertThat;

class AgentImplementationTest {

    @Test
    void keywordAgentShouldClassifyMultipleIntents() {
        MessageService ms = messageService();
        KeywordIntentAgent agent = new KeywordIntentAgent(ms);

        AgentResult billing = agent.run(new AgentContext("bill fee"));
        AgentResult maintenance = agent.run(new AgentContext("repair fix"));
        AgentResult general = agent.run(new AgentContext("hello"));

        assertThat(agent.name()).isEqualTo("intent");
        assertThat(agent.supports(new AgentContext("x"))).isTrue();
        assertThat(agent.supports(new AgentContext(" "))).isFalse();
        assertThat(agent.supports(null)).isFalse();

        assertThat(billing.data()).containsEntry("intent", "BILLING");
        assertThat(maintenance.data()).containsEntry("intent", "MAINTENANCE");
        assertThat(general.data()).containsEntry("intent", "GENERAL");
    }

    @Test
    void billingAndMaintenanceAgentsShouldSupportByKeyword() {
        MessageService ms = messageService();
        BillingAdviceAgent billingAgent = new BillingAdviceAgent(ms);
        MaintenanceAdviceAgent maintenanceAgent = new MaintenanceAdviceAgent(ms);

        AgentContext billingContext = new AgentContext("pay fee");
        AgentContext maintenanceContext = new AgentContext("need repair");

        assertThat(billingAgent.name()).isEqualTo("billing");
        assertThat(maintenanceAgent.name()).isEqualTo("maintenance");

        assertThat(billingAgent.supports(billingContext)).isTrue();
        assertThat(billingAgent.supports(maintenanceContext)).isFalse();
        assertThat(billingAgent.supports(new AgentContext(null))).isFalse();

        assertThat(maintenanceAgent.supports(maintenanceContext)).isTrue();
        assertThat(maintenanceAgent.supports(billingContext)).isFalse();
        assertThat(maintenanceAgent.supports(new AgentContext(null))).isFalse();

        assertThat(billingAgent.run(billingContext).summary()).isEqualTo("billing advice");
        assertThat(maintenanceAgent.run(maintenanceContext).summary()).isEqualTo("maintenance advice");

        AgentResult empty = AgentResult.of("a", "b");
        assertThat(empty.data()).isEmpty();
    }

    private MessageService messageService() {
        StaticMessageSource source = new StaticMessageSource();
        source.addMessage("agent.intent.identified", Locale.ENGLISH, "Intent identified: {0}");
        source.addMessage("agent.billing.advice", Locale.ENGLISH, "billing advice");
        source.addMessage("agent.maintenance.advice", Locale.ENGLISH, "maintenance advice");
        return new MessageService(source);
    }
}
