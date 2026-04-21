package upb.edu.co.fairticket.adapter.out.persistence.gateway;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import upb.edu.co.fairticket.adapter.out.persistence.mapper.EventPersistenceMapper;
import upb.edu.co.fairticket.adapter.out.persistence.repository.EventJpaRepository;
import upb.edu.co.fairticket.domain.model.Event;
import upb.edu.co.fairticket.domain.port.EventRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class EventRepositoryGateway implements EventRepository {

    private final EventJpaRepository jpaRepository;

    @Override
    public Event save(Event event) {
        return EventPersistenceMapper.toDomain(
            jpaRepository.save(EventPersistenceMapper.toJpa(event))
        );
    }

    @Override
    public Optional<Event> findById(UUID id) {
        return jpaRepository.findById(id).map(EventPersistenceMapper::toDomain);
    }

    @Override
    public List<Event> findAll() {
        return jpaRepository.findAll().stream()
            .map(EventPersistenceMapper::toDomain)
            .toList();
    }

    @Override
    public List<Event> findByOrganizerId(UUID organizerId) {
        return jpaRepository.findByOrganizerId(organizerId).stream()
            .map(EventPersistenceMapper::toDomain)
            .toList();
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }
}
