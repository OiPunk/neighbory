package io.oipunk.neighbory.agent.api;

import jakarta.validation.constraints.NotBlank;

/**
 * Multi-agent request payload.
 */
public record AssistRequest(
        @NotBlank(message = "{validation.agent.text.notBlank}")
        String text
) {
}
