package upb.edu.co.fairticket.domain.model;

import java.util.UUID;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

import upb.edu.co.fairticket.domain.model.enums.Role;
import upb.edu.co.fairticket.domain.model.valueobjects.Email;

@Getter
public class User {

    private UUID id;
    private String name;
    private Email email;
    private Role role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Setter
    private String passwordHash;

    @Setter
    private String rawPassword;

    public User(UUID id, String name, Email email, Role role, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.passwordHash = null;
        this.rawPassword = null;
    }

    public User(UUID id, String name, Email email, Role role, LocalDateTime createdAt,
                LocalDateTime updatedAt, String passwordHash, String rawPassword) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.passwordHash = passwordHash;
        this.rawPassword = rawPassword;
    }

    public static User createBuyer(String name, Email email) {
        return new User(UUID.randomUUID(), name, email, Role.BUYER, LocalDateTime.now(), LocalDateTime.now());
    }

    public static User createOrganizer(String name, Email email) {
        return new User(UUID.randomUUID(), name, email, Role.ORGANIZER, LocalDateTime.now(), LocalDateTime.now());
    }

    public static User createAdmin(String name, Email email) {
        return new User(UUID.randomUUID(), name, email, Role.ADMIN, LocalDateTime.now(), LocalDateTime.now());
    }

    public void updateProfile(String name, Email email) {
        this.name = name;
        this.email = email;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isAdmin() {
        return this.role.equals(Role.ADMIN);
    }

    public boolean isOrganizer() {
        return this.role.equals(Role.ORGANIZER);
    }

    public boolean isBuyer() {
        return this.role.equals(Role.BUYER);
    }
}