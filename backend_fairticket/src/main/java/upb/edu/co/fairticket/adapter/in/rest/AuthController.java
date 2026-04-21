package upb.edu.co.fairticket.adapter.in.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upb.edu.co.fairticket.adapter.in.rest.dto.request.LoginRequest;
import upb.edu.co.fairticket.adapter.in.rest.dto.request.RegisterUserRequest;
import upb.edu.co.fairticket.adapter.in.rest.dto.response.AuthResponse;
import upb.edu.co.fairticket.domain.model.User;
import upb.edu.co.fairticket.domain.model.enums.Role;
import upb.edu.co.fairticket.domain.usecase.user.LoginUseCase;
import upb.edu.co.fairticket.domain.usecase.user.RegisterUserUseCase;
import upb.edu.co.fairticket.security.JwtService;

@RestController
@RequestMapping("/api/verify")
public class AuthController {

    @Autowired
    private LoginUseCase loginUseCase;

    @Autowired
    private RegisterUserUseCase registerUserUseCase;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterUserRequest request) {
        Role role = Role.valueOf(request.role().toUpperCase());
        User user = registerUserUseCase.execute(
                request.name(),
                request.email(),
                request.password(),
                role
        );
        String token = jwtService.generateToken(
                user.getId().toString(),
                user.getEmail().value(),
                user.getRole().name()
        );
        return ResponseEntity.ok(new AuthResponse(
                token,
                user.getId(),
                user.getEmail().value(),
                user.getName(),
                user.getRole().name()
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        User user = loginUseCase.execute(request.getEmail(), request.getPassword());
        String token = jwtService.generateToken(
                user.getId().toString(),
                user.getEmail().value(),
                user.getRole().name()
        );
        return ResponseEntity.ok(new AuthResponse(
                token,
                user.getId(),
                user.getEmail().value(),
                user.getName(),
                user.getRole().name()
        ));
    }
}