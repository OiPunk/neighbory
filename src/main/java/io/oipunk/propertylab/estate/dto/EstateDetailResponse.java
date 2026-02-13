package io.oipunk.propertylab.estate.dto;

import java.util.List;

public record EstateDetailResponse(
        Long id,
        String code,
        String name,
        String address,
        String remark,
        long buildingCount,
        long unitCount,
        List<BuildingResponse> buildings
) {
}
