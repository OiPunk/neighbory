package io.oipunk.propertylab.estate;

import io.oipunk.propertylab.estate.dto.EstateCreateRequest;
import io.oipunk.propertylab.estate.dto.EstateUpdateRequest;
import io.oipunk.propertylab.estate.entity.Building;
import io.oipunk.propertylab.estate.entity.Estate;
import io.oipunk.propertylab.estate.entity.Unit;
import io.oipunk.propertylab.estate.repository.EstateRepository;
import io.oipunk.propertylab.estate.repository.EstateSummaryProjection;
import io.oipunk.propertylab.estate.service.EstateService;
import io.oipunk.propertylab.exception.BusinessException;
import io.oipunk.propertylab.exception.ResourceNotFoundException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EstateServiceTest {

    @Mock
    private EstateRepository estateRepository;

    private EstateService estateService;

    @BeforeEach
    void setUp() {
        estateService = new EstateService(estateRepository);
    }

    @Test
    void listShouldReturnSummary() {
        EstateSummaryProjection one = summary(1L, "ESTATE-11", "社区11", 2L, 10L);
        EstateSummaryProjection two = summary(2L, "ESTATE-22", "社区22", 3L, 20L);
        // Service 不再额外排序，排序语义由 Repository 查询保证；单测里也按正确顺序返回。
        when(estateRepository.findAllSummary()).thenReturn(List.of(one, two));

        var result = estateService.list();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).id()).isEqualTo(1L);
        assertThat(result.get(0).buildingCount()).isEqualTo(2L);
        assertThat(result.get(0).unitCount()).isEqualTo(10L);
    }

    @Test
    void detailShouldReturnNestedInfo() {
        Estate estate = estate(1L, "ESTATE-11", "社区11");
        Building b1 = building(11L, "B1", "1号楼", estate);
        Building b2 = building(12L, "B2", "2号楼", estate);
        Unit u1 = unit(101L, "U1", "1单元", b1);
        Unit u2 = unit(102L, "U2", "2单元", b1);
        Unit u3 = unit(103L, "U1", "1单元", b2);
        b1.setUnits(List.of(u2, u1));
        b2.setUnits(List.of(u3));
        estate.setBuildings(List.of(b2, b1));

        when(estateRepository.findById(1L)).thenReturn(Optional.of(estate));

        var detail = estateService.detail(1L);

        assertThat(detail.id()).isEqualTo(1L);
        assertThat(detail.buildingCount()).isEqualTo(2L);
        assertThat(detail.unitCount()).isEqualTo(3L);
        assertThat(detail.buildings().get(0).id()).isEqualTo(11L);
        assertThat(detail.buildings().get(0).units().get(0).id()).isEqualTo(101L);
    }

    @Test
    void detailShouldThrowWhenNotFound() {
        when(estateRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> estateService.detail(99L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createShouldThrowWhenCodeExists() {
        when(estateRepository.existsByCode("ESTATE-11")).thenReturn(true);

        assertThatThrownBy(() -> estateService.create(new EstateCreateRequest("ESTATE-11", "n", null, null)))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    void createShouldSaveAndReturn() {
        when(estateRepository.existsByCode("ESTATE-99")).thenReturn(false);
        when(estateRepository.save(any(Estate.class))).thenAnswer(invocation -> {
            Estate e = invocation.getArgument(0);
            e.setId(99L);
            return e;
        });

        var result = estateService.create(new EstateCreateRequest("  estate-99  ", "  社区99  ", " addr ", " remark "));

        assertThat(result.id()).isEqualTo(99L);
        assertThat(result.code()).isEqualTo("ESTATE-99");
        verify(estateRepository, times(1)).save(any(Estate.class));
    }

    @Test
    void createShouldThrowWhenCodeNormalizedToNull() {
        assertThatThrownBy(() -> estateService.create(new EstateCreateRequest("   ", "n", null, null)))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    void createAndUpdateShouldHandleBlankTextAsNull() {
        when(estateRepository.existsByCode("ESTATE-101")).thenReturn(false);
        when(estateRepository.save(any(Estate.class))).thenAnswer(invocation -> {
            Estate e = invocation.getArgument(0);
            if (e.getId() == null) {
                e.setId(101L);
            }
            return e;
        });
        var created = estateService.create(new EstateCreateRequest("estate-101", " n ", "   ", "   "));

        assertThat(created.address()).isNull();
        assertThat(created.remark()).isNull();

        Estate existing = estate(101L, "ESTATE-101", "社区101");
        when(estateRepository.findById(101L)).thenReturn(Optional.of(existing));
        when(estateRepository.save(existing)).thenReturn(existing);

        var updated = estateService.update(101L, new EstateUpdateRequest("   ", "   ", "   "));

        assertThat(updated.name()).isNull();
        assertThat(updated.address()).isNull();
        assertThat(updated.remark()).isNull();
    }

    @Test
    void updateShouldThrowWhenNotFound() {
        when(estateRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> estateService.update(1L, new EstateUpdateRequest("n", null, null)))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void updateShouldPersistChanges() {
        Estate estate = estate(1L, "ESTATE-11", "社区11");
        when(estateRepository.findById(1L)).thenReturn(Optional.of(estate));
        when(estateRepository.save(estate)).thenReturn(estate);

        var result = estateService.update(1L, new EstateUpdateRequest("新名称", "新地址", "新备注"));

        assertThat(result.name()).isEqualTo("新名称");
        assertThat(result.address()).isEqualTo("新地址");
    }

    @Test
    void updateShouldHandleNullFields() {
        Estate estate = estate(2L, "ESTATE-22", "社区22");
        when(estateRepository.findById(2L)).thenReturn(Optional.of(estate));
        when(estateRepository.save(estate)).thenReturn(estate);

        var result = estateService.update(2L, new EstateUpdateRequest(null, null, null));

        assertThat(result.name()).isNull();
        assertThat(result.address()).isNull();
        assertThat(result.remark()).isNull();
    }

    @Test
    void deleteShouldRemoveEntity() {
        Estate estate = estate(1L, "ESTATE-11", "社区11");
        when(estateRepository.findById(1L)).thenReturn(Optional.of(estate));

        estateService.delete(1L);

        verify(estateRepository, times(1)).delete(estate);
    }

    @Test
    void deleteShouldThrowWhenNotFound() {
        when(estateRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> estateService.delete(1L)).isInstanceOf(ResourceNotFoundException.class);
    }

    private Estate estate(Long id, String code, String name) {
        Estate estate = new Estate();
        estate.setId(id);
        estate.setCode(code);
        estate.setName(name);
        estate.setAddress("addr");
        estate.setRemark("remark");
        return estate;
    }

    private EstateSummaryProjection summary(Long id,
                                           String code,
                                           String name,
                                           long buildingCount,
                                           long unitCount) {
        return new EstateSummaryProjection() {
            @Override
            public Long getId() {
                return id;
            }

            @Override
            public String getCode() {
                return code;
            }

            @Override
            public String getName() {
                return name;
            }

            @Override
            public String getAddress() {
                return "addr";
            }

            @Override
            public String getRemark() {
                return "remark";
            }

            @Override
            public long getBuildingCount() {
                return buildingCount;
            }

            @Override
            public long getUnitCount() {
                return unitCount;
            }
        };
    }

    private Building building(Long id, String code, String name, Estate estate) {
        Building building = new Building();
        building.setId(id);
        building.setCode(code);
        building.setName(name);
        building.setEstate(estate);
        return building;
    }

    private Unit unit(Long id, String code, String name, Building building) {
        Unit unit = new Unit();
        unit.setId(id);
        unit.setCode(code);
        unit.setName(name);
        unit.setBuilding(building);
        return unit;
    }
}
