package io.oipunk.neighbory.common;

import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

/**
 * Message lookup facade for an English-only project.
 */
@Component
public class MessageService {

    private final MessageSource messageSource;

    public MessageService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String get(String key, Object... args) {
        return messageSource.getMessage(key, args, Locale.ENGLISH);
    }
}
