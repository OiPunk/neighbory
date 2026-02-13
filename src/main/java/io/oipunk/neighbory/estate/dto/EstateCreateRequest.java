package io.oipunk.neighbory.estate.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EstateCreateRequest(
        @NotBlank(message = "{validation.estate.code.notBlank}")
        @Size(max = 20, message = "{validation.estate.code.max}")
        String code,

        @NotBlank(message = "{validation.estate.name.notBlank}")
        @Size(max = 100, message = "{validation.estate.name.max}")
        String name,

        @Size(max = 255, message = "{validation.estate.address.max}")
        String address,

        @Size(max = 255, message = "{validation.estate.remark.max}")
        String remark
) {
}
