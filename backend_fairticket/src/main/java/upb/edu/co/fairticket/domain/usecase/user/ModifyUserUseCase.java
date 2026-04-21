package upb.edu.co.fairticket.domain.usecase.user;

import lombok.RequiredArgsConstructor;
import upb.edu.co.fairticket.domain.model.User;
import upb.edu.co.fairticket.domain.model.valueobjects.Email;
import upb.edu.co.fairticket.domain.port.UserRepository;
import upb.edu.co.fairticket.domain.exception.UserNotFoundException;
import java.util.UUID;

@RequiredArgsConstructor
public class ModifyUserUseCase {
    private final UserRepository userRepository;

    public User execute(UUID id, String name, String emailStr) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found: " + id));
        user.updateProfile(name, new Email(emailStr));
        return userRepository.save(user);
    }
}
