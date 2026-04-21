package upb.edu.co.fairticket.domain.port;

import upb.edu.co.fairticket.adapter.out.persistence.entity.UserJpaEntity;
import upb.edu.co.fairticket.adapter.out.persistence.mapper.UserPersistenceMapper;
import upb.edu.co.fairticket.domain.model.User;
import upb.edu.co.fairticket.domain.model.valueobjects.Email;
import java.util.Optional;
import java.util.List;
import java.util.UUID;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(UUID id);
    Optional<User> findByEmail(Email email);
    List<User> findAll();
    void deleteById(UUID id);
    Optional<User> findByEmail(String email);

}
