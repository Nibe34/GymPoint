package gympoint.backend.authservice.service;

import gympoint.backend.authservice.dto.*;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<AuthResponse> register(RegisterRequest request);
    ResponseEntity<AuthResponse> login(LoginRequest request);
    ResponseEntity<AuthResponse> refreshToken(RefreshTokenRequest request);
} 