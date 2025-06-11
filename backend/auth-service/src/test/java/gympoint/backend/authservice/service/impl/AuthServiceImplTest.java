package gympoint.backend.authservice.service.impl;

import gympoint.backend.authservice.client.UserServiceClient;
import gympoint.backend.authservice.dto.*;
import gympoint.backend.authservice.entity.RefreshToken;
import gympoint.backend.authservice.entity.User;
import gympoint.backend.authservice.entity.UserRole;
import gympoint.backend.authservice.repository.RefreshTokenRepository;
import gympoint.backend.authservice.repository.UserRepository;
import gympoint.backend.authservice.security.JwtService;
import gympoint.backend.common.enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UserServiceClient userServiceClient;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthServiceImpl authService;

    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;
    private UserDetailsResponse userDetailsResponse;
    private User user;
    private RefreshToken refreshToken;

    @BeforeEach
    void setUp() {
        registerRequest = new ClientRegisterRequest();
        registerRequest.setEmail("test@example.com");
        registerRequest.setPassword("password");
        registerRequest.setRole(Role.ROLE_CLIENT);

        loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password");

        userDetailsResponse = new UserDetailsResponse();
        userDetailsResponse.setUsername("test@example.com");
        userDetailsResponse.setPassword("encodedPassword");
        userDetailsResponse.setAuthorities(Collections.singletonList("ROLE_CLIENT"));

        user = new User();
        user.setEmail("test@example.com");
        user.setPassword("encodedPassword");
        user.setRole(UserRole.ROLE_CLIENT);

        refreshToken = new RefreshToken();
        refreshToken.setToken("refresh-token");
        refreshToken.setUser(user);
        refreshToken.setExpiryDate(LocalDateTime.now().plusDays(7));
    }

    @Test
    void register_Success() {
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(userServiceClient.createClient(any())).thenReturn(ResponseEntity.ok().build());
        when(userServiceClient.getUserByEmail(any())).thenReturn(userDetailsResponse);
        when(jwtService.generateToken(any(UserDetails.class))).thenReturn("jwt-token");
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn(user);
        when(refreshTokenRepository.save(any())).thenReturn(refreshToken);

        ResponseEntity<AuthResponse> response = authService.register(registerRequest);

        assertNotNull(response);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
        assertEquals("jwt-token", response.getBody().getAccessToken());
        assertEquals("refresh-token", response.getBody().getRefreshToken());
    }

    @Test
    void login_Success() {
        when(userServiceClient.getUserByEmail(any())).thenReturn(userDetailsResponse);
        when(jwtService.generateToken(any(UserDetails.class))).thenReturn("jwt-token");
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        when(refreshTokenRepository.save(any())).thenReturn(refreshToken);

        ResponseEntity<AuthResponse> response = authService.login(loginRequest);

        assertNotNull(response);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
        assertEquals("jwt-token", response.getBody().getAccessToken());
        assertEquals("refresh-token", response.getBody().getRefreshToken());
    }

    @Test
    void refreshToken_Success() {
        RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest();
        refreshTokenRequest.setRefreshToken("refresh-token");

        when(refreshTokenRepository.findByToken(any())).thenReturn(Optional.of(refreshToken));
        when(userServiceClient.getUserByEmail(any())).thenReturn(userDetailsResponse);
        when(jwtService.generateToken(any(UserDetails.class))).thenReturn("new-jwt-token");

        ResponseEntity<AuthResponse> response = authService.refreshToken(refreshTokenRequest);

        assertNotNull(response);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
        assertEquals("new-jwt-token", response.getBody().getAccessToken());
        assertEquals("refresh-token", response.getBody().getRefreshToken());
    }
} 