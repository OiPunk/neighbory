package io.oipunk.neighbory.parking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ParkingAssignRequest(
        @NotBlank(message = "{validation.parking.ownerName.notBlank}")
        @Size(max = 100, message = "{validation.parking.ownerName.max}")
        String ownerName
) {
}
