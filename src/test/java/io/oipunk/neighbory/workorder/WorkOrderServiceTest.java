package io.oipunk.neighbory.workorder;

import io.oipunk.neighbory.exception.ResourceNotFoundException;
import io.oipunk.neighbory.workorder.dto.WorkOrderCreateRequest;
import io.oipunk.neighbory.workorder.dto.WorkOrderUpdateStatusRequest;
import io.oipunk.neighbory.workorder.entity.WorkOrder;
import io.oipunk.neighbory.workorder.repository.WorkOrderRepository;
import io.oipunk.neighbory.workorder.service.WorkOrderService;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WorkOrderServiceTest {

    @Mock
    private WorkOrderRepository workOrderRepository;

    private WorkOrderService workOrderService;

    @BeforeEach
    void setUp() {
        workOrderService = new WorkOrderService(workOrderRepository);
    }

    @Test
    void listShouldSupportNullableStatus() {
        WorkOrder one = order(1L, "OPEN");
        WorkOrder two = order(2L, "DONE");
        when(workOrderRepository.findAll()).thenReturn(List.of(one, two));
        when(workOrderRepository.findByStatus("OPEN")).thenReturn(List.of(one));
        when(workOrderRepository.findByStatus(null)).thenReturn(List.of());

        assertThat(workOrderService.list(null)).hasSize(2);
        assertThat(workOrderService.list(" open ")).hasSize(1);
        assertThat(workOrderService.list("   ")).isEmpty();
    }

    @Test
    void createShouldSetDefaultsAndNormalize() {
        when(workOrderRepository.save(any(WorkOrder.class))).thenAnswer(invocation -> {
            WorkOrder o = invocation.getArgument(0);
            o.setId(9L);
            return o;
        });

        var response = workOrderService.create(new WorkOrderCreateRequest("  t  ", "  d  ", " high "));

        assertThat(response.id()).isEqualTo(9L);
        assertThat(response.priority()).isEqualTo("HIGH");
        assertThat(response.status()).isEqualTo("OPEN");
        assertThat(response.createdAt()).isNotNull();
    }

    @Test
    void updateStatusShouldUpdateWhenFound() {
        WorkOrder order = order(1L, "OPEN");
        when(workOrderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(workOrderRepository.save(order)).thenReturn(order);

        var response = workOrderService.updateStatus(1L, new WorkOrderUpdateStatusRequest(" done "));

        assertThat(response.status()).isEqualTo("DONE");
    }

    @Test
    void updateStatusShouldThrowWhenMissing() {
        when(workOrderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> workOrderService.updateStatus(1L, new WorkOrderUpdateStatusRequest("DONE")))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createShouldHandleBlankDescriptionAndPriority() {
        when(workOrderRepository.save(any(WorkOrder.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var response = workOrderService.create(new WorkOrderCreateRequest(" t ", "   ", "   "));

        assertThat(response.description()).isNull();
        assertThat(response.priority()).isNull();
    }

    @Test
    void updateStatusShouldHandleNullStatus() {
        WorkOrder order = order(2L, "OPEN");
        when(workOrderRepository.findById(2L)).thenReturn(Optional.of(order));
        when(workOrderRepository.save(order)).thenReturn(order);

        var response = workOrderService.updateStatus(2L, new WorkOrderUpdateStatusRequest(null));

        assertThat(response.status()).isNull();
    }

    private WorkOrder order(Long id, String status) {
        WorkOrder order = new WorkOrder();
        order.setId(id);
        order.setTitle("title");
        order.setDescription("desc");
        order.setPriority("HIGH");
        order.setStatus(status);
        order.setCreatedAt(Instant.now());
        return order;
    }
}
