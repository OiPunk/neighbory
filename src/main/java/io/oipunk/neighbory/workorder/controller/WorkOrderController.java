package io.oipunk.neighbory.workorder.controller;

import io.oipunk.neighbory.common.ApiResponse;
import io.oipunk.neighbory.common.MessageService;
import io.oipunk.neighbory.workorder.dto.WorkOrderCreateRequest;
import io.oipunk.neighbory.workorder.dto.WorkOrderResponse;
import io.oipunk.neighbory.workorder.dto.WorkOrderUpdateStatusRequest;
import io.oipunk.neighbory.workorder.service.WorkOrderService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/workorders")
public class WorkOrderController {

    private final WorkOrderService workOrderService;
    private final MessageService messageService;

    public WorkOrderController(WorkOrderService workOrderService, MessageService messageService) {
        this.workOrderService = workOrderService;
        this.messageService = messageService;
    }

    @GetMapping
    public ApiResponse<List<WorkOrderResponse>> list(@RequestParam(required = false) String status) {
        return ApiResponse.of(messageService.get("workorder.list.success"), workOrderService.list(status));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<WorkOrderResponse> create(@Valid @RequestBody WorkOrderCreateRequest request) {
        WorkOrderResponse response = workOrderService.create(request);
        return ApiResponse.of(messageService.get("workorder.create.success", response.id()), response);
    }

    @PatchMapping("/{id}/status")
    public ApiResponse<WorkOrderResponse> updateStatus(@PathVariable Long id,
                                                       @Valid @RequestBody WorkOrderUpdateStatusRequest request) {
        WorkOrderResponse response = workOrderService.updateStatus(id, request);
        return ApiResponse.of(messageService.get("workorder.status.success", id), response);
    }
}
