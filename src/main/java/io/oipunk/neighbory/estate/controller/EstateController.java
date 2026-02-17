package io.oipunk.neighbory.estate.controller;

import io.oipunk.neighbory.common.ApiResponse;
import io.oipunk.neighbory.common.MessageService;
import io.oipunk.neighbory.estate.dto.EstateCreateRequest;
import io.oipunk.neighbory.estate.dto.EstateDetailResponse;
import io.oipunk.neighbory.estate.dto.EstateSummaryResponse;
import io.oipunk.neighbory.estate.dto.EstateUpdateRequest;
import io.oipunk.neighbory.estate.service.EstateService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/estates")
public class EstateController {

    /**
     * The controller layer is responsible only for:
     * 1. request validation and DTO protocol mapping
     * 2. building a unified response envelope (`ApiResponse`)
     * 3. returning consistent response messages
     */
    private final EstateService estateService;
    private final MessageService messageService;

    public EstateController(EstateService estateService, MessageService messageService) {
        this.estateService = estateService;
        this.messageService = messageService;
    }

    @GetMapping
    public ApiResponse<List<EstateSummaryResponse>> list() {
        return ApiResponse.of(messageService.get("estate.list.success"), estateService.list());
    }

    @GetMapping("/{id}")
    public ApiResponse<EstateDetailResponse> detail(@PathVariable Long id) {
        return ApiResponse.of(messageService.get("estate.detail.success", id), estateService.detail(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<EstateDetailResponse> create(@Valid @RequestBody EstateCreateRequest request) {
        EstateDetailResponse created = estateService.create(request);
        return ApiResponse.of(messageService.get("estate.create.success", created.code()), created);
    }

    @PutMapping("/{id}")
    public ApiResponse<EstateDetailResponse> update(@PathVariable Long id, @Valid @RequestBody EstateUpdateRequest request) {
        EstateDetailResponse updated = estateService.update(id, request);
        return ApiResponse.of(messageService.get("estate.update.success", updated.code()), updated);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        estateService.delete(id);
        return ApiResponse.empty(messageService.get("estate.delete.success", id));
    }
}
