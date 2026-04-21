package upb.edu.co.fairticket.adapter.out.persistence.gateway;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import upb.edu.co.fairticket.adapter.out.persistence.mapper.UserPersistenceMapper;
import upb.edu.co.fairticket.adapter.out.persistence.repository.UserJpaRepository;
import upb.edu.co.fairticket.domain.model.User;
import upb.edu.co.fairticket.domain.model.valueobjects.Email;
import upb.edu.co.fairticket.domain.port.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserRepositoryGateway implements UserRepository {

    private static final Logger log = LoggerFactory.getLogger(UserRepositoryGateway.class);
    private final UserJpaRepository jpaRepository;

    @Override
    public User save(User user) {


        var jpaEntity = UserPersistenceMapper.toJpa(user);


        var savedEntity = jpaRepository.save(jpaEntity);


        var result = UserPersistenceMapper.toDomain(savedEntity);

        return result;
    }

    @Override
    public Optional<User> findById(UUID id) {
        return jpaRepository.findById(id).map(UserPersistenceMapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(Email email) {
        return jpaRepository.findByEmail(email.value()).map(UserPersistenceMapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepository.findByEmail(email).map(UserPersistenceMapper::toDomain);
    }

    @Override
    public List<User> findAll() {
        return jpaRepository.findAll().stream()
                .map(UserPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }
}