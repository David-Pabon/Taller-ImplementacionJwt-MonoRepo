package upb.edu.co.fairticket.domain.model;

public class UserDomain {
    private Long id;
    private String email;
    private String passwordHash;
    private String rawPassword;
    private String role;

    public UserDomain(Long id, String email, String passwordHash, String rawPassword, String role) {
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.rawPassword = rawPassword;
        this.role = role;
    }

    public static UserDomain create(String email, String passwordHash, String rawPassword, String role) {
        return new UserDomain(null, email, passwordHash, rawPassword, role);
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public String getRawPassword() { return rawPassword; }
    public void setRawPassword(String rawPassword) { this.rawPassword = rawPassword; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}