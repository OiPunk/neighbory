package io.oipunk.propertylab.workorder.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record WorkOrderCreateRequest(
        @NotBlank(message = "{validation.workorder.title.notBlank}")
        @Size(max = 100, message = "{validation.workorder.title.max}")
        String title,

        @Size(max = 255, message = "{validation.workorder.description.max}")
        String description,

        @NotBlank(message = "{validation.workorder.priority.notBlank}")
        @Size(max = 20, message = "{validation.workorder.priority.max}")
        String priority
) {
}
