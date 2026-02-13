package io.oipunk.propertylab.workorder.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record WorkOrderUpdateStatusRequest(
        @NotBlank(message = "{validation.workorder.status.notBlank}")
        @Size(max = 20, message = "{validation.workorder.status.max}")
        String status
) {
}
