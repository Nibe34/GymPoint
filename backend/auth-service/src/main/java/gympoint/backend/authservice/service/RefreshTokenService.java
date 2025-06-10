package gympoint.backend.authservice.service;

import gympoint.backend.authservice.entity.RefreshToken;
import gympoint.backend.authservice.entity.User;

import java.util.Optional;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(User user);
    RefreshToken verifyExpiration(RefreshToken token);
    void deleteRefreshToken(User user);
    RefreshToken findByToken(String token);
    void deleteRefreshTokensByUser(User user);
    Optional<RefreshToken> findByTokenOptional(String token);
} 