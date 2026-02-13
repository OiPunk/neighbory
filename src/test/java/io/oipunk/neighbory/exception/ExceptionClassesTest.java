package io.oipunk.neighbory.exception;

import io.oipunk.neighbory.common.LocaleMessageService;
import io.oipunk.neighbory.estate.dto.EstateCreateRequest;
import java.lang.reflect.Method;
import java.time.Instant;
import java.util.Map;
import java.util.Locale;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.StaticMessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.assertj.core.api.Assertions.assertThat;

class ExceptionClassesTest {

    @AfterEach
    void cleanup() {
        LocaleContextHolder.resetLocaleContext();
    }

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
        source.addMessage("error.common.title", Locale.SIMPLIFIED_CHINESE, "请求失败");
        source.addMessage("error.resource.notFound", Locale.SIMPLIFIED_CHINESE, "未找到{0}:{1}");
        source.addMessage("error.estate.codeExists", Locale.SIMPLIFIED_CHINESE, "编码重复:{0}");
        source.addMessage("error.validation.title", Locale.SIMPLIFIED_CHINESE, "参数校验失败");
        source.addMessage("error.validation.detail", Locale.SIMPLIFIED_CHINESE, "请求参数未通过校验");
        source.addMessage("error.internal", Locale.SIMPLIFIED_CHINESE, "内部错误");
        LocaleContextHolder.setLocale(Locale.SIMPLIFIED_CHINESE);
        LocaleMessageService messageService = new LocaleMessageService(source);

        GlobalExceptionHandler handler = new GlobalExceptionHandler(messageService);

        var notFound = handler.handleNotFound(new ResourceNotFoundException("Estate", 1L));
        assertThat(notFound.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(notFound.getTitle()).isEqualTo("请求失败");
        assertThat(notFound.getDetail()).contains("Estate");
        assertThat(notFound.getProperties()).containsKey("timestamp");
        assertThat(notFound.getProperties().get("timestamp")).isInstanceOf(Instant.class);

        var business = handler.handleBusiness(new BusinessException("error.estate.codeExists", HttpStatus.CONFLICT, "ESTATE-1"));
        assertThat(business.getStatus()).isEqualTo(HttpStatus.CONFLICT.value());
        assertThat(business.getDetail()).isEqualTo("编码重复:ESTATE-1");

        Method method = DummyHandler.class.getDeclaredMethod("dummy", EstateCreateRequest.class);
        MethodParameter parameter = new MethodParameter(method, 0);
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "request");
        bindingResult.addError(new FieldError("request", "code", "楼盘编码不能为空"));
        MethodArgumentNotValidException manve = new MethodArgumentNotValidException(parameter, bindingResult);

        var validation = handler.handleValidation(manve);
        assertThat(validation.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(validation.getTitle()).isEqualTo("参数校验失败");
        assertThat(validation.getProperties()).containsKey("errors");
        Map<String, String> errors = (Map<String, String>) validation.getProperties().get("errors");
        assertThat(errors).containsEntry("code", "楼盘编码不能为空");

        var fallback = handler.handleFallback(new RuntimeException("boom"));
        assertThat(fallback.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(fallback.getDetail()).isEqualTo("内部错误");
    }

    private static class DummyHandler {
        void dummy(EstateCreateRequest request) {
        }
    }
}
