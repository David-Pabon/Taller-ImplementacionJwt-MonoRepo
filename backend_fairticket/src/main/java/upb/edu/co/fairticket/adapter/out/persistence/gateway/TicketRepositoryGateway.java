package upb.edu.co.fairticket.adapter.out.persistence.gateway;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import upb.edu.co.fairticket.adapter.out.persistence.mapper.TicketPersistenceMapper;
import upb.edu.co.fairticket.adapter.out.persistence.repository.TicketJpaRepository;
import upb.edu.co.fairticket.domain.model.Ticket;
import upb.edu.co.fairticket.domain.port.TicketRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class TicketRepositoryGateway implements TicketRepository {

    private final TicketJpaRepository jpaRepository;

    @Override
    public Ticket save(Ticket ticket) {
        return TicketPersistenceMapper.toDomain(
            jpaRepository.save(TicketPersistenceMapper.toJpa(ticket))
        );
    }

    @Override
    public Optional<Ticket> findById(UUID id) {
        return jpaRepository.findById(id).map(TicketPersistenceMapper::toDomain);
    }

    @Override
    public List<Ticket> findByBuyerId(UUID buyerId) {
        return jpaRepository.findByBuyerId(buyerId).stream()
            .map(TicketPersistenceMapper::toDomain)
            .toList();
    }

    @Override
    public List<Ticket> findByEventId(UUID eventId) {
        return jpaRepository.findByEventId(eventId).stream()
            .map(TicketPersistenceMapper::toDomain)
            .toList();
    }

    @Override
    public int countByBuyerIdAndEventId(UUID buyerId, UUID eventId) {
        return jpaRepository.countByBuyerIdAndEventId(buyerId, eventId);
    }
}
