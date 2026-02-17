package io.oipunk.neighbory.common;

import java.time.Instant;

/**
 * Unified API response envelope for predictable client-side handling.
 */
public record ApiResponse<T>(String message, T data, Instant timestamp) {

    public static <T> ApiResponse<T> of(String message, T data) {
        return new ApiResponse<>(message, data, Instant.now());
    }

    public static ApiResponse<Void> empty(String message) {
        return new ApiResponse<>(message, null, Instant.now());
    }
}
