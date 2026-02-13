package io.oipunk.neighbory;

import io.oipunk.neighbory.agent.api.AssistRequest;
import io.oipunk.neighbory.agent.api.AssistResponse;
import io.oipunk.neighbory.agent.core.AgentContext;
import io.oipunk.neighbory.agent.core.AgentResult;
import io.oipunk.neighbory.billing.dto.BillingChargeCreateRequest;
import io.oipunk.neighbory.billing.dto.BillingChargeResponse;
import io.oipunk.neighbory.billing.entity.BillingCharge;
import io.oipunk.neighbory.parking.dto.ParkingAssignRequest;
import io.oipunk.neighbory.parking.dto.ParkingSpaceResponse;
import io.oipunk.neighbory.parking.entity.ParkingSpace;
import io.oipunk.neighbory.workorder.dto.WorkOrderCreateRequest;
import io.oipunk.neighbory.workorder.dto.WorkOrderResponse;
import io.oipunk.neighbory.workorder.dto.WorkOrderUpdateStatusRequest;
import io.oipunk.neighbory.workorder.entity.WorkOrder;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DomainModelObjectTest {

    @Test
    void newDomainEntitiesAndDtosShouldWork() {
        BillingCharge charge = new BillingCharge();
        charge.setId(1L);
        charge.setEstateCode("ESTATE-11");
        charge.setUnitCode("B1-U1");
        charge.setAmount(new BigDecimal("100"));
        charge.setDueDate(LocalDate.of(2026, 3, 1));
        charge.setPaid(false);
        charge.setRemark("remark");

        assertThat(charge.getId()).isEqualTo(1L);
        assertThat(charge.getEstateCode()).isEqualTo("ESTATE-11");
        assertThat(charge.getUnitCode()).isEqualTo("B1-U1");
        assertThat(charge.getAmount()).isEqualTo(new BigDecimal("100"));
        assertThat(charge.getDueDate()).isEqualTo(LocalDate.of(2026, 3, 1));
        assertThat(charge.isPaid()).isFalse();
        assertThat(charge.getRemark()).isEqualTo("remark");

        WorkOrder order = new WorkOrder();
        Instant now = Instant.now();
        order.setId(2L);
        order.setTitle("title");
        order.setDescription("desc");
        order.setPriority("HIGH");
        order.setStatus("OPEN");
        order.setCreatedAt(now);

        assertThat(order.getId()).isEqualTo(2L);
        assertThat(order.getTitle()).isEqualTo("title");
        assertThat(order.getDescription()).isEqualTo("desc");
        assertThat(order.getPriority()).isEqualTo("HIGH");
        assertThat(order.getStatus()).isEqualTo("OPEN");
        assertThat(order.getCreatedAt()).isEqualTo(now);

        ParkingSpace space = new ParkingSpace();
        space.setId(3L);
        space.setCode("P-001");
        space.setOccupied(true);
        space.setOwnerName("张三");

        assertThat(space.getId()).isEqualTo(3L);
        assertThat(space.getCode()).isEqualTo("P-001");
        assertThat(space.isOccupied()).isTrue();
        assertThat(space.getOwnerName()).isEqualTo("张三");

        BillingChargeCreateRequest createRequest = new BillingChargeCreateRequest("E", "U", new BigDecimal("1"), LocalDate.now(), "r");
        BillingChargeResponse chargeResponse = new BillingChargeResponse(1L, "E", "U", new BigDecimal("1"), LocalDate.now(), false, "r");
        WorkOrderCreateRequest workCreate = new WorkOrderCreateRequest("t", "d", "p");
        WorkOrderUpdateStatusRequest workStatus = new WorkOrderUpdateStatusRequest("DONE");
        WorkOrderResponse workResponse = new WorkOrderResponse(1L, "t", "d", "p", "OPEN", Instant.now());
        ParkingAssignRequest assignRequest = new ParkingAssignRequest("owner");
        ParkingSpaceResponse parkingResponse = new ParkingSpaceResponse(1L, "P", true, "o");

        assertThat(createRequest.estateCode()).isEqualTo("E");
        assertThat(chargeResponse.unitCode()).isEqualTo("U");
        assertThat(workCreate.priority()).isEqualTo("p");
        assertThat(workStatus.status()).isEqualTo("DONE");
        assertThat(workResponse.title()).isEqualTo("t");
        assertThat(assignRequest.ownerName()).isEqualTo("owner");
        assertThat(parkingResponse.code()).isEqualTo("P");

        AgentContext context = new AgentContext("hello", Locale.US);
        AgentResult result = AgentResult.of("agent", "summary", Map.of("k", "v"));
        AssistRequest assistRequest = new AssistRequest("q");
        AssistResponse assistResponse = new AssistResponse(List.of(result));

        assertThat(context.text()).isEqualTo("hello");
        assertThat(result.agent()).isEqualTo("agent");
        assertThat(result.data()).containsEntry("k", "v");
        assertThat(assistRequest.text()).isEqualTo("q");
        assertThat(assistResponse.steps()).hasSize(1);
    }
}
