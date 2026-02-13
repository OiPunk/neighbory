package io.oipunk.neighbory.billing;

import io.oipunk.neighbory.billing.dto.BillingChargeCreateRequest;
import io.oipunk.neighbory.billing.entity.BillingCharge;
import io.oipunk.neighbory.billing.repository.BillingChargeRepository;
import io.oipunk.neighbory.billing.service.BillingService;
import io.oipunk.neighbory.exception.ResourceNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BillingServiceTest {

    @Mock
    private BillingChargeRepository billingChargeRepository;

    private BillingService billingService;

    @BeforeEach
    void setUp() {
        billingService = new BillingService(billingChargeRepository);
    }

    @Test
    void listShouldSupportNullableFilter() {
        BillingCharge c1 = charge(1L, false);
        BillingCharge c2 = charge(2L, true);
        when(billingChargeRepository.findAll()).thenReturn(List.of(c1, c2));
        when(billingChargeRepository.findByPaid(true)).thenReturn(List.of(c2));
        when(billingChargeRepository.findByPaid(false)).thenReturn(List.of(c1));

        assertThat(billingService.list(null)).hasSize(2);
        assertThat(billingService.list(true)).hasSize(1);
        assertThat(billingService.list(false)).hasSize(1);
    }

    @Test
    void createShouldNormalizeAndDefaultPaidFalse() {
        when(billingChargeRepository.save(any(BillingCharge.class))).thenAnswer(invocation -> {
            BillingCharge c = invocation.getArgument(0);
            c.setId(9L);
            return c;
        });

        BillingChargeCreateRequest request = new BillingChargeCreateRequest(
                " estate-11 ",
                " b1-u1 ",
                new BigDecimal("99.50"),
                LocalDate.of(2026, 3, 1),
                "  monthly fee  "
        );

        var response = billingService.create(request);

        assertThat(response.id()).isEqualTo(9L);
        assertThat(response.estateCode()).isEqualTo("ESTATE-11");
        assertThat(response.unitCode()).isEqualTo("B1-U1");
        assertThat(response.paid()).isFalse();

        ArgumentCaptor<BillingCharge> captor = ArgumentCaptor.forClass(BillingCharge.class);
        verify(billingChargeRepository).save(captor.capture());
        assertThat(captor.getValue().isPaid()).isFalse();
    }

    @Test
    void markPaidShouldUpdateWhenExists() {
        BillingCharge c = charge(1L, false);
        when(billingChargeRepository.findById(1L)).thenReturn(Optional.of(c));
        when(billingChargeRepository.save(c)).thenReturn(c);

        var response = billingService.markPaid(1L);

        assertThat(response.paid()).isTrue();
    }

    @Test
    void markPaidShouldThrowWhenMissing() {
        when(billingChargeRepository.findById(7L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> billingService.markPaid(7L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createShouldHandleNullAndBlankText() {
        when(billingChargeRepository.save(any(BillingCharge.class))).thenAnswer(invocation -> invocation.getArgument(0));

        BillingChargeCreateRequest request = new BillingChargeCreateRequest(
                null,
                "  ",
                new BigDecimal("1"),
                LocalDate.of(2026, 3, 10),
                "   "
        );
        var response = billingService.create(request);

        assertThat(response.estateCode()).isNull();
        assertThat(response.unitCode()).isNull();
        assertThat(response.remark()).isNull();
    }

    private BillingCharge charge(Long id, boolean paid) {
        BillingCharge charge = new BillingCharge();
        charge.setId(id);
        charge.setEstateCode("ESTATE-11");
        charge.setUnitCode("B1-U1");
        charge.setAmount(new BigDecimal("100"));
        charge.setDueDate(LocalDate.of(2026, 3, 1));
        charge.setPaid(paid);
        charge.setRemark("remark");
        return charge;
    }
}
