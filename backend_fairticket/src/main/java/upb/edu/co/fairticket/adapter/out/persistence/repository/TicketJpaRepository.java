package upb.edu.co.fairticket.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import upb.edu.co.fairticket.adapter.out.persistence.entity.TicketJpaEntity;

import java.util.List;
import java.util.UUID;

public interface TicketJpaRepository extends JpaRepository<TicketJpaEntity, UUID> {
    List<TicketJpaEntity> findByBuyerId(UUID buyerId);
    List<TicketJpaEntity> findByEventId(UUID eventId);
    int countByBuyerIdAndEventId(UUID buyerId, UUID eventId);
}
