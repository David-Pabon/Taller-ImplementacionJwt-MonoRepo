package upb.edu.co.fairticket.domain.port;

public interface HashEngine {
    String hash(String rawPassword);
    boolean matches(String rawPassword, String hashedPassword);
}