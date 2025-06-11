package gympoint.backend.authservice.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    private UserDetails userDetails;
    private static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
    private static final long EXPIRATION = 86400000; // 24 hours

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(jwtService, "secretKey", SECRET_KEY);
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", EXPIRATION);

        userDetails = new User(
            "test@example.com",
            "password",
            Collections.singletonList(new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_USER"))
        );
    }

    @Test
    void generateToken_Success() {
        String token = jwtService.generateToken(userDetails);

        assertNotNull(token);
        assertTrue(token.split("\\.").length == 3); // JWT has 3 parts
    }

    @Test
    void generateTokenWithExtraClaims_Success() {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", "ADMIN");

        String token = jwtService.generateToken(extraClaims, userDetails);

        assertNotNull(token);
        assertTrue(token.split("\\.").length == 3);
    }

    @Test
    void extractUsername_Success() {
        String token = jwtService.generateToken(userDetails);
        String username = jwtService.extractUsername(token);

        assertEquals(userDetails.getUsername(), username);
    }

    @Test
    void isTokenValid_Success() {
        String token = jwtService.generateToken(userDetails);
        boolean isValid = jwtService.isTokenValid(token, userDetails);

        assertTrue(isValid);
    }

    @Test
    void isTokenValid_InvalidUser() {
        String token = jwtService.generateToken(userDetails);
        UserDetails differentUser = new User(
            "different@example.com",
            "password",
            Collections.singletonList(new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_USER"))
        );

        boolean isValid = jwtService.isTokenValid(token, differentUser);

        assertFalse(isValid);
    }
} 