package upb.edu.co.fairticket.domain.usecase.purchase;

import lombok.RequiredArgsConstructor;
import upb.edu.co.fairticket.domain.model.Purchase;
import upb.edu.co.fairticket.domain.port.PurchaseRepository;

import java.util.UUID;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class GetPurchaseUseCase {
    private final PurchaseRepository purchaseRepository;

    public Optional<Purchase> execute(UUID purchaseId) {
        return purchaseRepository.findById(purchaseId);
    }
    
    public List<Purchase> getByBuyer(UUID buyerId) {
        return purchaseRepository.findByBuyerId(buyerId);
    }
}
