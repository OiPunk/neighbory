package io.oipunk.propertylab.estate.dto;

public record EstateSummaryResponse(
        Long id,
        String code,
        String name,
        String address,
        String remark,
        long buildingCount,
        long unitCount
) {
}
