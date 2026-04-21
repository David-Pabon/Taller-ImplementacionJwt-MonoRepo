package upb.edu.co.fairticket.domain.usecase.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upb.edu.co.fairticket.domain.model.User;
import upb.edu.co.fairticket.domain.model.enums.Role;
import upb.edu.co.fairticket.domain.model.valueobjects.Email;
import upb.edu.co.fairticket.domain.port.HashEngine;
import upb.edu.co.fairticket.domain.exception.DomainException;
import upb.edu.co.fairticket.domain.port.UserRepository;

@Service
public class RegisterUserUseCase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HashEngine hashEngine;

    public User registerBuyer(String name, String emailStr) {
        Email email = new Email(emailStr);
        if (userRepository.findByEmail(emailStr).isPresent()) {
            throw new DomainException("Email already registered: " + emailStr);
        }
        User user = User.createBuyer(name, email);
        return userRepository.save(user);
    }

    public User registerOrganizer(String name, String emailStr) {
        Email email = new Email(emailStr);
        if (userRepository.findByEmail(emailStr).isPresent()) {
            throw new DomainException("Email already registered: " + emailStr);
        }
        User user = User.createOrganizer(name, email);
        return userRepository.save(user);
    }

    public User execute(String name, String emailStr, String rawPassword, Role role) {
        Email email = new Email(emailStr);

        if (userRepository.findByEmail(emailStr).isPresent()) {
            throw new DomainException("Email already registered: " + emailStr);
        }

        User user;
        switch (role) {
            case BUYER:
                user = User.createBuyer(name, email);
                break;
            case ORGANIZER:
                user = User.createOrganizer(name, email);
                break;
            case ADMIN:
                user = User.createAdmin(name, email);
                break;
            default:
                throw new DomainException("Invalid role: " + role);
        }

        String hashedPassword = hashEngine.hash(rawPassword);

        user.setPasswordHash(hashedPassword);
        user.setRawPassword(rawPassword);

        return userRepository.save(user);
    }
}