package upb.edu.co.fairticket.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import upb.edu.co.fairticket.adapter.out.persistence.entity.EventJpaEntity;

import java.util.List;
import java.util.UUID;

public interface EventJpaRepository extends JpaRepository<EventJpaEntity, UUID> {
    List<EventJpaEntity> findByOrganizerId(UUID organizerId);
}
