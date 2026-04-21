package upb.edu.co.fairticket.domain.usecase.purchase;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import upb.edu.co.fairticket.domain.model.Purchase;
import upb.edu.co.fairticket.domain.model.valueobjects.Money;
import upb.edu.co.fairticket.domain.port.PurchaseRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetPurchaseUseCaseTest {

    @Mock
    private PurchaseRepository purchaseRepository;

    @InjectMocks
    private GetPurchaseUseCase getPurchaseUseCase;

    private final UUID purchaseId = UUID.randomUUID();
    private final UUID buyerId = UUID.randomUUID();

    @Test
    void testExecuteByIdSuccess() {
        Purchase purchase = Purchase.create(buyerId, UUID.randomUUID(), Collections.emptyList(), Money.cop(100));
        when(purchaseRepository.findById(purchaseId)).thenReturn(Optional.of(purchase));

        Optional<Purchase> result = getPurchaseUseCase.execute(purchaseId);

        assertTrue(result.isPresent());
        assertEquals(purchase, result.get());
    }

    @Test
    void testExecuteByIdNotFound() {
        when(purchaseRepository.findById(purchaseId)).thenReturn(Optional.empty());

        Optional<Purchase> result = getPurchaseUseCase.execute(purchaseId);

        assertTrue(result.isEmpty());
    }

    @Test
    void testGetByBuyer() {
        Purchase p1 = Purchase.create(buyerId, UUID.randomUUID(), Collections.emptyList(), Money.cop(100));
        Purchase p2 = Purchase.create(buyerId, UUID.randomUUID(), Collections.emptyList(), Money.cop(100));
        when(purchaseRepository.findByBuyerId(buyerId)).thenReturn(Arrays.asList(p1, p2));

        List<Purchase> result = getPurchaseUseCase.getByBuyer(buyerId);

        assertEquals(2, result.size());
    }
}
