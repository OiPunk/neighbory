package io.oipunk.neighbory.exception;

import io.oipunk.neighbory.common.LocaleMessageService;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Unified exception output:
 * - uses the Spring 6 / Boot 3 recommended `ProblemDetail` model
 * - supports localization for both title and detail fields
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final LocaleMessageService messageService;

    public GlobalExceptionHandler(LocaleMessageService messageService) {
        this.messageService = messageService;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleNotFound(ResourceNotFoundException ex) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        detail.setTitle(messageService.get("error.common.title"));
        detail.setDetail(messageService.get("error.resource.notFound", ex.getResourceName(), ex.getResourceId()));
        detail.setProperty("timestamp", Instant.now());
        return detail;
    }

    @ExceptionHandler(BusinessException.class)
    public ProblemDetail handleBusiness(BusinessException ex) {
        ProblemDetail detail = ProblemDetail.forStatus(ex.getStatus());
        detail.setTitle(messageService.get("error.common.title"));
        detail.setDetail(messageService.get(ex.getMessageKey(), ex.getArgs()));
        detail.setProperty("timestamp", Instant.now());
        return detail;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(MethodArgumentNotValidException ex) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        detail.setTitle(messageService.get("error.validation.title"));
        detail.setDetail(messageService.get("error.validation.detail"));

        Map<String, String> fieldErrors = new LinkedHashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(error.getField(), error.getDefaultMessage());
        }
        detail.setProperty("errors", fieldErrors);
        detail.setProperty("timestamp", Instant.now());
        return detail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleFallback(Exception ex) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        detail.setTitle(messageService.get("error.common.title"));
        detail.setDetail(messageService.get("error.internal"));
        detail.setProperty("timestamp", Instant.now());
        return detail;
    }
}
