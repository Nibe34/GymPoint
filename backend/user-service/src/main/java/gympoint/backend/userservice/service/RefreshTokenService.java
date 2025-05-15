package gympoint.backend.userservice.service;

import gympoint.backend.userservice.entity.RefreshToken;
import gympoint.backend.userservice.entity.User;
import java.util.Optional;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(User user);
    RefreshToken verifyExpiration(RefreshToken token);
    void deleteRefreshToken(String token);
    void deleteRefreshTokensByUser(User user);
    Optional<RefreshToken> findByToken(String token);
} 