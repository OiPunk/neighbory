package io.oipunk.propertylab.billing.repository;

import io.oipunk.propertylab.billing.entity.BillingCharge;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillingChargeRepository extends JpaRepository<BillingCharge, Long> {

    List<BillingCharge> findByPaid(boolean paid);
}
