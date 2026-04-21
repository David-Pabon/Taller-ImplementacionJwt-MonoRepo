package upb.edu.co.fairticket.domain.usecase.user;

import lombok.RequiredArgsConstructor;
import upb.edu.co.fairticket.domain.model.User;
import upb.edu.co.fairticket.domain.port.UserRepository;
import java.util.List;

@RequiredArgsConstructor
public class ListUserUseCase {
    private final UserRepository userRepository;

    public List<User> execute() {
        return userRepository.findAll();
    }
}
