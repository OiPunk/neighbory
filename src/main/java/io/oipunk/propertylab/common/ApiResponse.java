package io.oipunk.propertylab.common;

import java.time.Instant;

/**
 * 统一 API 响应体，便于前端与新手同学快速形成固定响应心智模型。
 */
public record ApiResponse<T>(String message, T data, Instant timestamp) {

    public static <T> ApiResponse<T> of(String message, T data) {
        return new ApiResponse<>(message, data, Instant.now());
    }

    public static ApiResponse<Void> empty(String message) {
        return new ApiResponse<>(message, null, Instant.now());
    }
}
