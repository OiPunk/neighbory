package io.oipunk.neighbory.agent.api;

import jakarta.validation.constraints.NotBlank;

/**
 * 多 Agent 协作输入。
 */
public record AssistRequest(
        @NotBlank(message = "{validation.agent.text.notBlank}")
        String text
) {
}
