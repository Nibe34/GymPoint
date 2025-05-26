package gympoint.backend.workoutservice.config;

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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.http.Cookie;

import java.io.IOException;
import java.util.Arrays;
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
        try {
            logger.debug("Processing request to: {}", request.getRequestURI());

            if (shouldNotFilter(request)) {
                logger.debug("Skipping JWT filter for: {}", request.getRequestURI());
                filterChain.doFilter(request, response);
                return;
            }

            String jwt = getJwtFromRequest(request);
            logger.debug("JWT token from request: {}", jwt != null ? "present" : "not present");

            if (StringUtils.hasText(jwt) && jwtService.isTokenValid(jwt)) {
                String username = jwtService.extractUsername(jwt);
                String role = jwtService.extractRole(jwt);
                logger.debug("Valid JWT token for user: {} with role: {}", username, role);

                // Remove all ROLE_ prefixes
                while (role.startsWith("ROLE_")) {
                    role = role.substring(5);
                }
                logger.debug("Cleaned role: {}", role);

                // Add single ROLE_ prefix
                String finalRole = "ROLE_" + role;
                logger.debug("Final role with prefix: {}", finalRole);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        Collections.singletonList(new SimpleGrantedAuthority(finalRole))
                );
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
                logger.debug("Authentication set in SecurityContext for user: {}", username);
            } else {
                logger.debug("Invalid or missing JWT token");
            }
        } catch (Exception ex) {
            logger.error("Could not set user authentication in security context", ex);
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        boolean shouldNotFilter = path.startsWith("/api/auth") ||
                path.startsWith("/v3/api-docs") ||
                path.startsWith("/swagger-ui");
        logger.debug("Checking if should filter path: {} - result: {}", path, shouldNotFilter);
        return shouldNotFilter;
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        // Спочатку перевіряємо заголовок Authorization
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            logger.debug("JWT found in Authorization header");
            return bearerToken.substring(7);
        }

        // Якщо заголовок відсутній, перевіряємо кукі
        String token = getCookieValue(request, "access_token");
        if (StringUtils.hasText(token)) {
            logger.debug("JWT found in access_token cookie");
            return token;
        }

        logger.debug("No JWT found in Authorization header or cookies");
        return null;
    }

    private String getCookieValue(HttpServletRequest request, String name) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(name)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
} 