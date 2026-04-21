package upb.edu.co.fairticket.domain.usecase.user;

import lombok.RequiredArgsConstructor;
import upb.edu.co.fairticket.domain.port.UserRepository;
import upb.edu.co.fairticket.domain.exception.UserNotFoundException;
import java.util.UUID;

@RequiredArgsConstructor
public class DeleteUserUseCase {
    private final UserRepository userRepository;

    public void execute(UUID id) {
        userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found: " + id));
        userRepository.deleteById(id);
    }
}
