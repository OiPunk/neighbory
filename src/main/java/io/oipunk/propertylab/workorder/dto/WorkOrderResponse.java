package io.oipunk.propertylab.workorder.dto;

import java.time.Instant;

public record WorkOrderResponse(
        Long id,
        String title,
        String description,
        String priority,
        String status,
        Instant createdAt
) {
}
