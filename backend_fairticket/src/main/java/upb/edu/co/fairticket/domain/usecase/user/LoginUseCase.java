package upb.edu.co.fairticket.domain.usecase.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import upb.edu.co.fairticket.domain.model.User;
import upb.edu.co.fairticket.domain.port.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class LoginUseCase {

    private static final Logger log = LoggerFactory.getLogger(LoginUseCase.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User execute(String email, String rawPassword) {

        java.util.Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isEmpty()) {
            throw new RuntimeException("Credenciales inválidas");
        }

        User user = userOpt.get();

        String storedHash = user.getPasswordHash();

        if (storedHash == null) {
            throw new RuntimeException("Credenciales inválidas - Usuario sin contraseña configurada");
        }


        boolean matches = passwordEncoder.matches(rawPassword, storedHash);

        if (!matches) {
            throw new RuntimeException("Credenciales inválidas");
        }

        return user;
    }
}