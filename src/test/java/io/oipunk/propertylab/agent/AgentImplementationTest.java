package io.oipunk.propertylab.agent;

import io.oipunk.propertylab.agent.core.AgentContext;
import io.oipunk.propertylab.agent.core.AgentResult;
import io.oipunk.propertylab.agent.impl.BillingAdviceAgent;
import io.oipunk.propertylab.agent.impl.KeywordIntentAgent;
import io.oipunk.propertylab.agent.impl.MaintenanceAdviceAgent;
import io.oipunk.propertylab.common.LocaleMessageService;
import java.util.Locale;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.StaticMessageSource;

import static org.assertj.core.api.Assertions.assertThat;

class AgentImplementationTest {

    @AfterEach
    void cleanup() {
        LocaleContextHolder.resetLocaleContext();
    }

    @Test
    void keywordAgentShouldClassifyMultipleIntents() {
        LocaleMessageService ms = messageService();
        KeywordIntentAgent agent = new KeywordIntentAgent(ms);
        LocaleContextHolder.setLocale(Locale.US);

        AgentResult billing = agent.run(new AgentContext("bill fee", Locale.US));
        AgentResult maintenance = agent.run(new AgentContext("repair fix", Locale.US));
        AgentResult general = agent.run(new AgentContext("hello", Locale.US));

        assertThat(agent.name()).isEqualTo("intent");
        assertThat(agent.supports(new AgentContext("x", Locale.US))).isTrue();
        assertThat(agent.supports(new AgentContext(" ", Locale.US))).isFalse();
        assertThat(agent.supports(null)).isFalse();

        assertThat(billing.data()).containsEntry("intent", "BILLING");
        assertThat(maintenance.data()).containsEntry("intent", "MAINTENANCE");
        assertThat(general.data()).containsEntry("intent", "GENERAL");
    }

    @Test
    void billingAndMaintenanceAgentsShouldSupportByKeyword() {
        LocaleMessageService ms = messageService();
        BillingAdviceAgent billingAgent = new BillingAdviceAgent(ms);
        MaintenanceAdviceAgent maintenanceAgent = new MaintenanceAdviceAgent(ms);
        LocaleContextHolder.setLocale(Locale.US);

        AgentContext billingContext = new AgentContext("pay fee", Locale.US);
        AgentContext maintenanceContext = new AgentContext("need repair", Locale.US);

        assertThat(billingAgent.name()).isEqualTo("billing");
        assertThat(maintenanceAgent.name()).isEqualTo("maintenance");

        assertThat(billingAgent.supports(billingContext)).isTrue();
        assertThat(billingAgent.supports(maintenanceContext)).isFalse();
        assertThat(billingAgent.supports(new AgentContext(null, Locale.US))).isFalse();

        assertThat(maintenanceAgent.supports(maintenanceContext)).isTrue();
        assertThat(maintenanceAgent.supports(billingContext)).isFalse();
        assertThat(maintenanceAgent.supports(new AgentContext(null, Locale.US))).isFalse();

        assertThat(billingAgent.run(billingContext).summary()).isEqualTo("billing advice");
        assertThat(maintenanceAgent.run(maintenanceContext).summary()).isEqualTo("maintenance advice");

        AgentResult empty = AgentResult.of("a", "b");
        assertThat(empty.data()).isEmpty();
    }

    private LocaleMessageService messageService() {
        StaticMessageSource source = new StaticMessageSource();
        source.addMessage("agent.intent.identified", Locale.US, "Intent identified: {0}");
        source.addMessage("agent.billing.advice", Locale.US, "billing advice");
        source.addMessage("agent.maintenance.advice", Locale.US, "maintenance advice");
        return new LocaleMessageService(source);
    }
}
