package io.oipunk.propertylab.billing.service;

import io.oipunk.propertylab.billing.dto.BillingChargeCreateRequest;
import io.oipunk.propertylab.billing.dto.BillingChargeResponse;
import io.oipunk.propertylab.billing.entity.BillingCharge;
import io.oipunk.propertylab.billing.repository.BillingChargeRepository;
import io.oipunk.propertylab.exception.ResourceNotFoundException;
import java.util.List;
import java.util.Locale;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 账单子域服务。
 */
@Service
public class BillingService {

    private final BillingChargeRepository billingChargeRepository;

    public BillingService(BillingChargeRepository billingChargeRepository) {
        this.billingChargeRepository = billingChargeRepository;
    }

    @Transactional(readOnly = true)
    public List<BillingChargeResponse> list(Boolean paid) {
        List<BillingCharge> charges = paid == null
                ? billingChargeRepository.findAll()
                : billingChargeRepository.findByPaid(paid);
        return charges.stream().map(this::toResponse).toList();
    }

    @Transactional
    public BillingChargeResponse create(BillingChargeCreateRequest request) {
        BillingCharge charge = new BillingCharge();
        charge.setEstateCode(normalizeUpper(request.estateCode()));
        charge.setUnitCode(normalizeUpper(request.unitCode()));
        charge.setAmount(request.amount());
        charge.setDueDate(request.dueDate());
        charge.setRemark(normalize(request.remark()));
        charge.setPaid(false);
        return toResponse(billingChargeRepository.save(charge));
    }

    @Transactional
    public BillingChargeResponse markPaid(Long id) {
        BillingCharge charge = billingChargeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BillingCharge", id));
        charge.setPaid(true);
        return toResponse(billingChargeRepository.save(charge));
    }

    private BillingChargeResponse toResponse(BillingCharge charge) {
        return new BillingChargeResponse(
                charge.getId(),
                charge.getEstateCode(),
                charge.getUnitCode(),
                charge.getAmount(),
                charge.getDueDate(),
                charge.isPaid(),
                charge.getRemark()
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
