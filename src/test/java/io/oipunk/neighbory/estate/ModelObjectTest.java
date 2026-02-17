package io.oipunk.neighbory.estate;

import io.oipunk.neighbory.estate.dto.BuildingResponse;
import io.oipunk.neighbory.estate.dto.EstateCreateRequest;
import io.oipunk.neighbory.estate.dto.EstateDetailResponse;
import io.oipunk.neighbory.estate.dto.EstateSummaryResponse;
import io.oipunk.neighbory.estate.dto.EstateUpdateRequest;
import io.oipunk.neighbory.estate.dto.UnitResponse;
import io.oipunk.neighbory.estate.entity.Building;
import io.oipunk.neighbory.estate.entity.Estate;
import io.oipunk.neighbory.estate.entity.Unit;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ModelObjectTest {

    @Test
    void entityGettersSettersShouldWork() {
        Estate estate = new Estate();
        estate.setId(1L);
        estate.setCode("ESTATE-1");
        estate.setName("Estate 1");
        estate.setAddress("addr");
        estate.setRemark("remark");

        Building building = new Building();
        building.setId(2L);
        building.setCode("B1");
        building.setName("Building 1");
        building.setEstate(estate);

        Unit unit = new Unit();
        unit.setId(3L);
        unit.setCode("U1");
        unit.setName("Unit 1");
        unit.setBuilding(building);

        building.setUnits(List.of(unit));
        estate.setBuildings(List.of(building));

        assertThat(estate.getId()).isEqualTo(1L);
        assertThat(estate.getCode()).isEqualTo("ESTATE-1");
        assertThat(estate.getName()).isEqualTo("Estate 1");
        assertThat(estate.getAddress()).isEqualTo("addr");
        assertThat(estate.getRemark()).isEqualTo("remark");
        assertThat(estate.getBuildings()).hasSize(1);

        assertThat(building.getId()).isEqualTo(2L);
        assertThat(building.getCode()).isEqualTo("B1");
        assertThat(building.getName()).isEqualTo("Building 1");
        assertThat(building.getEstate()).isEqualTo(estate);
        assertThat(building.getUnits()).hasSize(1);

        assertThat(unit.getId()).isEqualTo(3L);
        assertThat(unit.getCode()).isEqualTo("U1");
        assertThat(unit.getName()).isEqualTo("Unit 1");
        assertThat(unit.getBuilding()).isEqualTo(building);
    }

    @Test
    void dtoRecordsShouldExposeValues() {
        EstateCreateRequest create = new EstateCreateRequest("ESTATE-1", "name", "addr", "remark");
        EstateUpdateRequest update = new EstateUpdateRequest("name2", "addr2", "remark2");
        UnitResponse unit = new UnitResponse(1L, "U1", "Unit 1");
        BuildingResponse building = new BuildingResponse(2L, "B1", "Building 1", 1, List.of(unit));
        EstateSummaryResponse summary = new EstateSummaryResponse(1L, "ESTATE-1", "name", "addr", "remark", 1, 1);
        EstateDetailResponse detail = new EstateDetailResponse(1L, "ESTATE-1", "name", "addr", "remark", 1, 1, List.of(building));

        assertThat(create.code()).isEqualTo("ESTATE-1");
        assertThat(create.name()).isEqualTo("name");
        assertThat(create.address()).isEqualTo("addr");
        assertThat(create.remark()).isEqualTo("remark");

        assertThat(update.name()).isEqualTo("name2");
        assertThat(update.address()).isEqualTo("addr2");
        assertThat(update.remark()).isEqualTo("remark2");

        assertThat(unit.id()).isEqualTo(1L);
        assertThat(unit.code()).isEqualTo("U1");
        assertThat(unit.name()).isEqualTo("Unit 1");

        assertThat(building.id()).isEqualTo(2L);
        assertThat(building.code()).isEqualTo("B1");
        assertThat(building.name()).isEqualTo("Building 1");
        assertThat(building.unitCount()).isEqualTo(1);
        assertThat(building.units()).containsExactly(unit);

        assertThat(summary.id()).isEqualTo(1L);
        assertThat(summary.code()).isEqualTo("ESTATE-1");
        assertThat(summary.name()).isEqualTo("name");
        assertThat(summary.address()).isEqualTo("addr");
        assertThat(summary.remark()).isEqualTo("remark");
        assertThat(summary.buildingCount()).isEqualTo(1);
        assertThat(summary.unitCount()).isEqualTo(1);

        assertThat(detail.id()).isEqualTo(1L);
        assertThat(detail.code()).isEqualTo("ESTATE-1");
        assertThat(detail.name()).isEqualTo("name");
        assertThat(detail.address()).isEqualTo("addr");
        assertThat(detail.remark()).isEqualTo("remark");
        assertThat(detail.buildingCount()).isEqualTo(1);
        assertThat(detail.unitCount()).isEqualTo(1);
        assertThat(detail.buildings()).containsExactly(building);
    }
}
