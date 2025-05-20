package gympoint.backend.trainerservice.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        logger.debug("Received Authorization header: {}", authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            logger.debug("No valid Authorization header found");
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);
        logger.debug("Extracted JWT token: {}", jwt);

        if (jwtService.isTokenValid(jwt)) {
            String userEmail = jwtService.extractUsername(jwt);
            String role = jwtService.extractRole(jwt);
            logger.debug("JWT parsed successfully. Email: {}, Role: {}", userEmail, role);

            // Remove all ROLE_ prefixes
            while (role.startsWith("ROLE_")) {
                role = role.substring(5);
            }
            logger.debug("Cleaned role: {}", role);

            // Add single ROLE_ prefix
            String finalRole = "ROLE_" + role;
            logger.debug("Final role with prefix: {}", finalRole);

            // Create authorities with proper ROLE_ prefix
            var authorities = Collections.singletonList(new SimpleGrantedAuthority(finalRole));
            logger.debug("Created authorities: {}", authorities);

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userEmail,
                    null,
                    authorities
            );
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
            logger.debug("Authentication set in SecurityContext. Authorities: {}", authorities);
        } else {
            logger.error("Invalid JWT token");
        }

        filterChain.doFilter(request, response);
    }
} 