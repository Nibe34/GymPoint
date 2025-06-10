package gympoint.backend.authservice.service.impl;

import gympoint.backend.authservice.client.UserServiceClient;
import gympoint.backend.authservice.dto.*;
import gympoint.backend.authservice.entity.RefreshToken;
import gympoint.backend.authservice.entity.User;
import gympoint.backend.authservice.entity.UserRole;
import gympoint.backend.authservice.repository.RefreshTokenRepository;
import gympoint.backend.authservice.repository.UserRepository;
import gympoint.backend.authservice.security.JwtService;
import gympoint.backend.authservice.service.AuthService;
import gympoint.backend.common.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserServiceClient userServiceClient;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public ResponseEntity<AuthResponse> register(RegisterRequest request) {
        // Encode password before sending to user service
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        
        ResponseEntity<?> response;
        switch (request.getRole()) {
            case ROLE_CLIENT:
                response = userServiceClient.createClient((ClientRegisterRequest) request);
                break;
            case ROLE_TRAINER:
                response = userServiceClient.createTrainer((TrainerRegisterRequest) request);
                break;
            case ROLE_ADMIN:
                response = userServiceClient.createAdmin((AdminRegisterRequest) request);
                break;
            default:
                throw new IllegalArgumentException("Invalid role: " + request.getRole());
        }
        
        return createAuthResponse(request.getEmail());
    }

    @Override
    @Transactional
    public ResponseEntity<AuthResponse> login(LoginRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );
        
        var userDetailsResponse = userServiceClient.getUserByEmail(request.getEmail());
        var userDetails = convertToUserDetails(userDetailsResponse);
        var jwtToken = jwtService.generateToken(userDetails);
        
        // Get or create user in auth service database
        var user = userRepository.findByEmail(request.getEmail())
            .orElseGet(() -> {
                var newUser = convertToUser(userDetailsResponse);
                return userRepository.save(newUser);
            });
        
        var refreshToken = createRefreshToken(user);
        
        return ResponseEntity.ok(new AuthResponse(
            jwtToken,
            refreshToken.getToken()
        ));
    }

    @Override
    public ResponseEntity<AuthResponse> refreshToken(RefreshTokenRequest request) {
        return refreshTokenRepository.findByToken(request.getRefreshToken())
            .map(refreshToken -> {
                if (refreshToken.isExpired()) {
                    refreshTokenRepository.delete(refreshToken);
                    throw new RuntimeException("Refresh token was expired");
                }
                
                var userDetailsResponse = userServiceClient.getUserByEmail(refreshToken.getUser().getEmail());
                var userDetails = convertToUserDetails(userDetailsResponse);
                var accessToken = jwtService.generateToken(userDetails);
                
                return ResponseEntity.ok(new AuthResponse(
                    accessToken,
                    request.getRefreshToken()
                ));
            })
            .orElseThrow(() -> new RuntimeException("Refresh token is not in database"));
    }

    private UserDetails convertToUserDetails(UserDetailsResponse response) {
        return new org.springframework.security.core.userdetails.User(
            response.getUsername(),
            response.getPassword(),
            response.isEnabled(),
            response.isAccountNonExpired(),
            response.isCredentialsNonExpired(),
            response.isAccountNonLocked(),
            response.getAuthorities().stream()
                .map(SimpleGrantedAuthority::new)
                .toList()
        );
    }

    private RefreshToken createRefreshToken(User user) {
        var refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setExpiryDate(LocalDateTime.now().plusDays(7));
        refreshToken.setToken(UUID.randomUUID().toString());
        
        return refreshTokenRepository.save(refreshToken);
    }

    private User convertToUser(UserDetailsResponse response) {
        var user = new User();
        user.setEmail(response.getUsername());
        user.setPassword(response.getPassword());
        user.setRole(convertRole(response.getAuthorities().iterator().next()));
        user.setFirstName(response.getFirstName());
        user.setLastName(response.getLastName());
        return user;
    }

    private UserRole convertRole(String authority) {
        return switch (authority) {
            case "ROLE_CLIENT" -> UserRole.ROLE_CLIENT;
            case "ROLE_TRAINER" -> UserRole.ROLE_TRAINER;
            case "ROLE_ADMIN" -> UserRole.ROLE_ADMIN;
            default -> throw new IllegalArgumentException("Invalid role: " + authority);
        };
    }

    private ResponseEntity<AuthResponse> createAuthResponse(String email) {
        var userDetailsResponse = userServiceClient.getUserByEmail(email);
        var userDetails = convertToUserDetails(userDetailsResponse);
        var jwtToken = jwtService.generateToken(userDetails);
        
        // Get or create user in auth service database
        var user = userRepository.findByEmail(email)
            .orElseGet(() -> {
                var newUser = convertToUser(userDetailsResponse);
                return userRepository.save(newUser);
            });
        
        var refreshToken = createRefreshToken(user);
        
        return ResponseEntity.ok(new AuthResponse(jwtToken, refreshToken.getToken()));
    }
} 