package io.oipunk.neighbory.common;

import java.util.Locale;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.StaticMessageSource;

import static org.assertj.core.api.Assertions.assertThat;

class CommonAndConfigTest {

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
    void messageServiceShouldUseEnglishMessages() {
        StaticMessageSource source = new StaticMessageSource();
        source.addMessage("hello", Locale.ENGLISH, "Hello {0}");
        source.addMessage("hello", Locale.FRANCE, "Bonjour {0}");
        MessageService service = new MessageService(source);

        assertThat(service.get("hello", "Tom")).isEqualTo("Hello Tom");
    }
}
