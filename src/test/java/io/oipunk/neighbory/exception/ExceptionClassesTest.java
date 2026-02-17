package io.oipunk.neighbory.exception;

import io.oipunk.neighbory.common.MessageService;
import io.oipunk.neighbory.estate.dto.EstateCreateRequest;
import java.lang.reflect.Method;
import java.time.Instant;
import java.util.Locale;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.StaticMessageSource;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.assertj.core.api.Assertions.assertThat;

class ExceptionClassesTest {

    @Test
    void resourceNotFoundExceptionShouldExposeFields() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Estate", 123L);
        assertThat(ex.getResourceName()).isEqualTo("Estate");
        assertThat(ex.getResourceId()).isEqualTo(123L);
        assertThat(ex.getMessage()).contains("Estate").contains("123");
    }

    @Test
    void businessExceptionShouldExposeFields() {
        BusinessException ex = new BusinessException("error.key", HttpStatus.CONFLICT, "A");
        assertThat(ex.getMessageKey()).isEqualTo("error.key");
        assertThat(ex.getStatus()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(ex.getArgs()).containsExactly("A");
    }

    @Test
    @SuppressWarnings("unchecked")
    void globalExceptionHandlerShouldBuildProblemDetail() throws Exception {
        StaticMessageSource source = new StaticMessageSource();
        source.addMessage("error.common.title", Locale.ENGLISH, "Request failed");
        source.addMessage("error.resource.notFound", Locale.ENGLISH, "{0} with id {1} was not found.");
        source.addMessage("error.estate.codeExists", Locale.ENGLISH, "Estate code already exists: {0}.");
        source.addMessage("error.validation.title", Locale.ENGLISH, "Validation failed");
        source.addMessage("error.validation.detail", Locale.ENGLISH, "Input payload did not pass validation.");
        source.addMessage("error.internal", Locale.ENGLISH, "Unexpected server error.");
        MessageService messageService = new MessageService(source);

        GlobalExceptionHandler handler = new GlobalExceptionHandler(messageService);

        var notFound = handler.handleNotFound(new ResourceNotFoundException("Estate", 1L));
        assertThat(notFound.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(notFound.getTitle()).isEqualTo("Request failed");
        assertThat(notFound.getDetail()).contains("Estate").contains("1");
        assertThat(notFound.getProperties()).containsKey("timestamp");
        assertThat(notFound.getProperties().get("timestamp")).isInstanceOf(Instant.class);

        var business = handler.handleBusiness(new BusinessException("error.estate.codeExists", HttpStatus.CONFLICT, "ESTATE-1"));
        assertThat(business.getStatus()).isEqualTo(HttpStatus.CONFLICT.value());
        assertThat(business.getDetail()).isEqualTo("Estate code already exists: ESTATE-1.");

        Method method = DummyHandler.class.getDeclaredMethod("dummy", EstateCreateRequest.class);
        MethodParameter parameter = new MethodParameter(method, 0);
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "request");
        bindingResult.addError(new FieldError("request", "code", "Estate code is required"));
        MethodArgumentNotValidException manve = new MethodArgumentNotValidException(parameter, bindingResult);

        var validation = handler.handleValidation(manve);
        assertThat(validation.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(validation.getTitle()).isEqualTo("Validation failed");
        assertThat(validation.getProperties()).containsKey("errors");
        Map<String, String> errors = (Map<String, String>) validation.getProperties().get("errors");
        assertThat(errors).containsEntry("code", "Estate code is required");

        var fallback = handler.handleFallback(new RuntimeException("boom"));
        assertThat(fallback.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(fallback.getDetail()).isEqualTo("Unexpected server error.");
    }

    private static class DummyHandler {
        void dummy(EstateCreateRequest request) {
        }
    }
}
