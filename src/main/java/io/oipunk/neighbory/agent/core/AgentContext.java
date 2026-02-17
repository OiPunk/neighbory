package io.oipunk.neighbory.agent.core;

import java.util.Locale;

/**
 * Agent context carrying user input and collaboration state.
 *
 * Kept intentionally lightweight for learning clarity.
 * It can be extended with user profile, session state, tools, and data handles.
 */
public record AgentContext(String text, Locale locale) {
}
