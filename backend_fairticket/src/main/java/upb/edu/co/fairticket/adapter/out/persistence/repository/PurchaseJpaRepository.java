package upb.edu.co.fairticket.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import upb.edu.co.fairticket.adapter.out.persistence.entity.PurchaseJpaEntity;

import java.util.List;
import java.util.UUID;

public interface PurchaseJpaRepository extends JpaRepository<PurchaseJpaEntity, UUID> {
    List<PurchaseJpaEntity> findByBuyerId(UUID buyerId);
}
