package io.oipunk.propertylab.billing.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record BillingChargeResponse(
        Long id,
        String estateCode,
        String unitCode,
        BigDecimal amount,
        LocalDate dueDate,
        boolean paid,
        String remark
) {
}
