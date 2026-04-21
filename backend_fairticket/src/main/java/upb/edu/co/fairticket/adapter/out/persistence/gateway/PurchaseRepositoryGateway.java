package upb.edu.co.fairticket.adapter.out.persistence.gateway;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import upb.edu.co.fairticket.adapter.out.persistence.mapper.PurchasePersistenceMapper;
import upb.edu.co.fairticket.adapter.out.persistence.repository.PurchaseJpaRepository;
import upb.edu.co.fairticket.domain.model.Purchase;
import upb.edu.co.fairticket.domain.port.PurchaseRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PurchaseRepositoryGateway implements PurchaseRepository {

    private final PurchaseJpaRepository jpaRepository;

    @Override
    public Purchase save(Purchase purchase) {
        return PurchasePersistenceMapper.toDomain(
            jpaRepository.save(PurchasePersistenceMapper.toJpa(purchase))
        );
    }

    @Override
    public Optional<Purchase> findById(UUID id) {
        return jpaRepository.findById(id).map(PurchasePersistenceMapper::toDomain);
    }

    @Override
    public List<Purchase> findByBuyerId(UUID buyerId) {
        return jpaRepository.findByBuyerId(buyerId).stream()
            .map(PurchasePersistenceMapper::toDomain)
            .toList();
    }
}
