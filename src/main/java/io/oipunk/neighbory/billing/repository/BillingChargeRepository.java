package io.oipunk.neighbory.billing.repository;

import io.oipunk.neighbory.billing.entity.BillingCharge;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillingChargeRepository extends JpaRepository<BillingCharge, Long> {

    List<BillingCharge> findByPaid(boolean paid);
}
