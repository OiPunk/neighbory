package io.oipunk.neighbory.common;

import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * 国际化消息读取门面，屏蔽 MessageSource 细节。
 */
@Component
public class LocaleMessageService {

    private final MessageSource messageSource;

    public LocaleMessageService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String get(String key, Object... args) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(key, args, locale);
    }
}
