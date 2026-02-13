package io.oipunk.neighbory.parking.dto;

public record ParkingSpaceResponse(
        Long id,
        String code,
        boolean occupied,
        String ownerName
) {
}
