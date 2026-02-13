package io.oipunk.propertylab.estate.service;

import io.oipunk.propertylab.estate.dto.BuildingResponse;
import io.oipunk.propertylab.estate.dto.EstateCreateRequest;
import io.oipunk.propertylab.estate.dto.EstateDetailResponse;
import io.oipunk.propertylab.estate.dto.EstateSummaryResponse;
import io.oipunk.propertylab.estate.dto.EstateUpdateRequest;
import io.oipunk.propertylab.estate.dto.UnitResponse;
import io.oipunk.propertylab.estate.entity.Estate;
import io.oipunk.propertylab.estate.repository.EstateRepository;
import io.oipunk.propertylab.estate.repository.EstateSummaryProjection;
import io.oipunk.propertylab.exception.BusinessException;
import io.oipunk.propertylab.exception.ResourceNotFoundException;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EstateService {

    /**
     * Service 层聚焦领域逻辑，不承载 HTTP 协议细节。
     */
    private final EstateRepository estateRepository;

    public EstateService(EstateRepository estateRepository) {
        this.estateRepository = estateRepository;
    }

    @Transactional(readOnly = true)
    public List<EstateSummaryResponse> list() {
        // 教学点：列表页优先用聚合查询，一次 SQL 返回统计字段，避免 N+1。
        return estateRepository.findAllSummary().stream()
                .map(this::toSummary)
                .toList();
    }

    @Transactional(readOnly = true)
    public EstateDetailResponse detail(Long id) {
        // 教学点：统一抛出领域异常，由全局异常处理器转换为 ProblemDetail。
        Estate estate = estateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estate", id));
        return toDetail(estate);
    }

    @Transactional
    public EstateDetailResponse create(EstateCreateRequest request) {
        String normalizedCode = normalizeCode(request.code());
        if (normalizedCode == null) {
            // code 的空校验由 Bean Validation 负责，这里仅做防御式处理，避免 NPE。
            throw new BusinessException("error.validation.badRequest", HttpStatus.BAD_REQUEST);
        }
        if (estateRepository.existsByCode(normalizedCode)) {
            throw new BusinessException("error.estate.codeExists", HttpStatus.CONFLICT, normalizedCode);
        }
        Estate estate = new Estate();
        estate.setCode(normalizedCode);
        estate.setName(normalizeText(request.name()));
        estate.setAddress(normalizeText(request.address()));
        estate.setRemark(normalizeText(request.remark()));
        Estate saved = estateRepository.save(estate);
        return toDetail(saved);
    }

    @Transactional
    public EstateDetailResponse update(Long id, EstateUpdateRequest request) {
        Estate estate = estateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estate", id));
        estate.setName(normalizeText(request.name()));
        estate.setAddress(normalizeText(request.address()));
        estate.setRemark(normalizeText(request.remark()));
        Estate saved = estateRepository.save(estate);
        return toDetail(saved);
    }

    @Transactional
    public void delete(Long id) {
        Estate estate = estateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estate", id));
        estateRepository.delete(estate);
    }

    private EstateSummaryResponse toSummary(EstateSummaryProjection estate) {
        return new EstateSummaryResponse(
                estate.getId(),
                estate.getCode(),
                estate.getName(),
                estate.getAddress(),
                estate.getRemark(),
                estate.getBuildingCount(),
                estate.getUnitCount()
        );
    }

    private EstateDetailResponse toDetail(Estate estate) {
        List<BuildingResponse> buildings = estate.getBuildings().stream()
                .sorted(Comparator.comparing(b -> b.getId()))
                .map(building -> {
                    List<UnitResponse> units = building.getUnits().stream()
                            .sorted(Comparator.comparing(u -> u.getId()))
                            .map(unit -> new UnitResponse(unit.getId(), unit.getCode(), unit.getName()))
                            .toList();
                    return new BuildingResponse(building.getId(), building.getCode(), building.getName(), units.size(), units);
                }).toList();

        long buildingCount = buildings.size();
        long unitCount = buildings.stream().mapToLong(BuildingResponse::unitCount).sum();

        return new EstateDetailResponse(
                estate.getId(),
                estate.getCode(),
                estate.getName(),
                estate.getAddress(),
                estate.getRemark(),
                buildingCount,
                unitCount,
                buildings
        );
    }

    private String normalizeCode(String code) {
        String normalized = normalizeText(code);
        if (normalized == null) {
            return null;
        }
        return normalized.toUpperCase(Locale.ROOT);
    }

    private String normalizeText(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
