package io.oipunk.neighbory.workorder.service;

import io.oipunk.neighbory.exception.ResourceNotFoundException;
import io.oipunk.neighbory.workorder.dto.WorkOrderCreateRequest;
import io.oipunk.neighbory.workorder.dto.WorkOrderResponse;
import io.oipunk.neighbory.workorder.dto.WorkOrderUpdateStatusRequest;
import io.oipunk.neighbory.workorder.entity.WorkOrder;
import io.oipunk.neighbory.workorder.repository.WorkOrderRepository;
import java.time.Instant;
import java.util.List;
import java.util.Locale;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WorkOrderService {

    private final WorkOrderRepository workOrderRepository;

    public WorkOrderService(WorkOrderRepository workOrderRepository) {
        this.workOrderRepository = workOrderRepository;
    }

    @Transactional(readOnly = true)
    public List<WorkOrderResponse> list(String status) {
        List<WorkOrder> orders = status == null
                ? workOrderRepository.findAll()
                : workOrderRepository.findByStatus(normalizeUpper(status));
        return orders.stream().map(this::toResponse).toList();
    }

    @Transactional
    public WorkOrderResponse create(WorkOrderCreateRequest request) {
        WorkOrder order = new WorkOrder();
        order.setTitle(normalize(request.title()));
        order.setDescription(normalize(request.description()));
        order.setPriority(normalizeUpper(request.priority()));
        order.setStatus("OPEN");
        order.setCreatedAt(Instant.now());
        return toResponse(workOrderRepository.save(order));
    }

    @Transactional
    public WorkOrderResponse updateStatus(Long id, WorkOrderUpdateStatusRequest request) {
        WorkOrder order = workOrderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("WorkOrder", id));
        order.setStatus(normalizeUpper(request.status()));
        return toResponse(workOrderRepository.save(order));
    }

    private WorkOrderResponse toResponse(WorkOrder order) {
        return new WorkOrderResponse(
                order.getId(),
                order.getTitle(),
                order.getDescription(),
                order.getPriority(),
                order.getStatus(),
                order.getCreatedAt()
        );
    }

    private String normalizeUpper(String value) {
        String normalized = normalize(value);
        if (normalized == null) {
            return null;
        }
        return normalized.toUpperCase(Locale.ROOT);
    }

    private String normalize(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
