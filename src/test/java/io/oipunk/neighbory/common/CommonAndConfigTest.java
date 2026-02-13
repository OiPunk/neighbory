package io.oipunk.neighbory.common;

import io.oipunk.neighbory.config.I18nConfig;
import java.util.Locale;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.StaticMessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.servlet.LocaleResolver;

import static org.assertj.core.api.Assertions.assertThat;

class CommonAndConfigTest {

    @AfterEach
    void tearDown() {
        LocaleContextHolder.resetLocaleContext();
    }

    @Test
    void apiResponseFactoryMethodsShouldWork() {
        ApiResponse<String> ok = ApiResponse.of("ok", "payload");
        ApiResponse<Void> empty = ApiResponse.empty("deleted");

        assertThat(ok.message()).isEqualTo("ok");
        assertThat(ok.data()).isEqualTo("payload");
        assertThat(ok.timestamp()).isNotNull();

        assertThat(empty.message()).isEqualTo("deleted");
        assertThat(empty.data()).isNull();
        assertThat(empty.timestamp()).isNotNull();
    }

    @Test
    void localeMessageServiceShouldUseCurrentLocale() {
        StaticMessageSource source = new StaticMessageSource();
        source.addMessage("hello", Locale.US, "Hello {0}");
        source.addMessage("hello", Locale.SIMPLIFIED_CHINESE, "你好 {0}");
        LocaleMessageService service = new LocaleMessageService(source);

        LocaleContextHolder.setLocale(Locale.US);
        assertThat(service.get("hello", "Tom")).isEqualTo("Hello Tom");

        LocaleContextHolder.setLocale(Locale.SIMPLIFIED_CHINESE);
        assertThat(service.get("hello", "Tom")).isEqualTo("你好 Tom");
    }

    @Test
    void i18nConfigShouldExposeDefaultLocaleAndMessageSource() {
        I18nConfig config = new I18nConfig();

        LocaleResolver resolver = config.localeResolver();
        assertThat(resolver).isNotNull();

        var messageSource = config.messageSource();
        assertThat(messageSource).isNotNull();
    }
}
