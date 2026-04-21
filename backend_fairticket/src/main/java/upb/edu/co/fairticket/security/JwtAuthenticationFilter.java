package upb.edu.co.fairticket.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        String method = request.getMethod();


        String authHeader = request.getHeader("Authorization");


        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);


            boolean isValid = jwtService.isTokenValid(token);

            if (isValid) {
                String userId = jwtService.extractUserId(token);
                String role = jwtService.extractRole(token);

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        userId,
                        null,
                        List.of(new SimpleGrantedAuthority("ROLE_" + role))
                );

                SecurityContextHolder.getContext().setAuthentication(auth);
            } else {
                log.warn(" Token inválido o expirado");
            }
        } else {
            if (authHeader == null) {
                log.info("No hay header Authorization");
            } else {
                log.info("Header no comienza con 'Bearer '");
            }
        }


        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            log.info("SecurityContext después del filtro - Authenticated: {}, Authorities: {}",
                    authentication.isAuthenticated(), authentication.getAuthorities());
        } else {
            log.info("SecurityContext después del filtro - ES NULL");
        }

        filterChain.doFilter(request, response);
    }
}