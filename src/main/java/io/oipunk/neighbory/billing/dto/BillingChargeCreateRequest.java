package io.oipunk.neighbory.billing.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

public record BillingChargeCreateRequest(
        @NotBlank(message = "{validation.billing.estateCode.notBlank}")
        @Size(max = 20, message = "{validation.billing.estateCode.max}")
        String estateCode,

        @NotBlank(message = "{validation.billing.unitCode.notBlank}")
        @Size(max = 20, message = "{validation.billing.unitCode.max}")
        String unitCode,

        @NotNull(message = "{validation.billing.amount.notNull}")
        @DecimalMin(value = "0.01", message = "{validation.billing.amount.min}")
        BigDecimal amount,

        @NotNull(message = "{validation.billing.dueDate.notNull}")
        LocalDate dueDate,

        @Size(max = 255, message = "{validation.billing.remark.max}")
        String remark
) {
}
