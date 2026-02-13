package io.oipunk.neighbory.workorder.repository;

import io.oipunk.neighbory.workorder.entity.WorkOrder;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkOrderRepository extends JpaRepository<WorkOrder, Long> {

    List<WorkOrder> findByStatus(String status);
}
