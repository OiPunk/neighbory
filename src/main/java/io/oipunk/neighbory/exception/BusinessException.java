package io.oipunk.neighbory.exception;

import org.springframework.http.HttpStatus;

public class BusinessException extends RuntimeException {

    private final String messageKey;
    private final Object[] args;
    private final HttpStatus status;

    public BusinessException(String messageKey, HttpStatus status, Object... args) {
        this.messageKey = messageKey;
        this.args = args;
        this.status = status;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public Object[] getArgs() {
        return args;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
