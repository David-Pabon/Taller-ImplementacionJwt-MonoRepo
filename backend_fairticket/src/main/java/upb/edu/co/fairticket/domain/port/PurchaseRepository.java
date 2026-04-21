package upb.edu.co.fairticket.domain.port;

import upb.edu.co.fairticket.domain.model.Purchase;
import java.util.Optional;
import java.util.List;
import java.util.UUID;

public interface PurchaseRepository {
    Purchase save(Purchase purchase);
    Optional<Purchase> findById(UUID id);
    List<Purchase> findByBuyerId(UUID buyerId);
}
