package io.oipunk.neighbory.agent.api;

import io.oipunk.neighbory.agent.core.AgentContext;
import io.oipunk.neighbory.agent.core.AgentOrchestrator;
import io.oipunk.neighbory.common.ApiResponse;
import io.oipunk.neighbory.common.MessageService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Demo API endpoint for multi-agent collaboration.
 *
 * Works locally without external LLM services. The implementation can later be
 * replaced by Spring AI / LLM-backed agents.
 */
@RestController
@RequestMapping("/api/v1/agents")
public class AgentController {

    private final AgentOrchestrator orchestrator;
    private final MessageService messageService;

    public AgentController(AgentOrchestrator orchestrator, MessageService messageService) {
        this.orchestrator = orchestrator;
        this.messageService = messageService;
    }

    @PostMapping("/assist")
    public ApiResponse<AssistResponse> assist(@Valid @RequestBody AssistRequest request) {
        var steps = orchestrator.assist(new AgentContext(request.text()));
        return ApiResponse.of(messageService.get("agent.assist.success"), new AssistResponse(steps));
    }
}
