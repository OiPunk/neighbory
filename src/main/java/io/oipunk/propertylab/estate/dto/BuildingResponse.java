package io.oipunk.propertylab.estate.dto;

import java.util.List;

public record BuildingResponse(Long id, String code, String name, long unitCount, List<UnitResponse> units) {
}
