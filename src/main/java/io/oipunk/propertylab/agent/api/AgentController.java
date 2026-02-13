package io.oipunk.propertylab.agent.api;

import io.oipunk.propertylab.agent.core.AgentContext;
import io.oipunk.propertylab.agent.core.AgentOrchestrator;
import io.oipunk.propertylab.common.ApiResponse;
import io.oipunk.propertylab.common.LocaleMessageService;
import jakarta.validation.Valid;
import java.util.Locale;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 多 Agent 协作演示接口。
 *
 * 不依赖外部 LLM，可本地跑通；后续可把实现替换为 Spring AI 的 LLM Agent。
 */
@RestController
@RequestMapping("/api/v1/agents")
public class AgentController {

    private final AgentOrchestrator orchestrator;
    private final LocaleMessageService messageService;

    public AgentController(AgentOrchestrator orchestrator, LocaleMessageService messageService) {
        this.orchestrator = orchestrator;
        this.messageService = messageService;
    }

    @PostMapping("/assist")
    public ApiResponse<AssistResponse> assist(@Valid @RequestBody AssistRequest request) {
        Locale locale = LocaleContextHolder.getLocale();
        var steps = orchestrator.assist(new AgentContext(request.text(), locale));
        return ApiResponse.of(messageService.get("agent.assist.success"), new AssistResponse(steps));
    }
}
