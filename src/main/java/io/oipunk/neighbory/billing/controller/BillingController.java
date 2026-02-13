package io.oipunk.neighbory.billing.controller;

import io.oipunk.neighbory.billing.dto.BillingChargeCreateRequest;
import io.oipunk.neighbory.billing.dto.BillingChargeResponse;
import io.oipunk.neighbory.billing.service.BillingService;
import io.oipunk.neighbory.common.ApiResponse;
import io.oipunk.neighbory.common.LocaleMessageService;
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
@RequestMapping("/api/v1/billing/charges")
public class BillingController {

    private final BillingService billingService;
    private final LocaleMessageService messageService;

    public BillingController(BillingService billingService, LocaleMessageService messageService) {
        this.billingService = billingService;
        this.messageService = messageService;
    }

    @GetMapping
    public ApiResponse<List<BillingChargeResponse>> list(@RequestParam(required = false) Boolean paid) {
        return ApiResponse.of(messageService.get("billing.list.success"), billingService.list(paid));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<BillingChargeResponse> create(@Valid @RequestBody BillingChargeCreateRequest request) {
        BillingChargeResponse response = billingService.create(request);
        return ApiResponse.of(messageService.get("billing.create.success", response.id()), response);
    }

    @PatchMapping("/{id}/pay")
    public ApiResponse<BillingChargeResponse> markPaid(@PathVariable Long id) {
        BillingChargeResponse response = billingService.markPaid(id);
        return ApiResponse.of(messageService.get("billing.pay.success", id), response);
    }
}
