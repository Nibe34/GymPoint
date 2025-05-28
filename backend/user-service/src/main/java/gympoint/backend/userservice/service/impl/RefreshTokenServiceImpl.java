package gympoint.backend.userservice.service.impl;

import gympoint.backend.userservice.entity.RefreshToken;
import gympoint.backend.userservice.entity.User;
import gympoint.backend.userservice.repository.RefreshTokenRepository;
import gympoint.backend.userservice.service.RefreshTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private static final Logger logger = LoggerFactory.getLogger(RefreshTokenServiceImpl.class);

    private final RefreshTokenRepository refreshTokenRepository;
    private final long refreshTokenDurationMs;
    
    @PersistenceContext
    private EntityManager entityManager;

    public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository,
                                   @Value("${app.jwtRefreshExpirationMs:604800000}") long refreshTokenDurationMs) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.refreshTokenDurationMs = refreshTokenDurationMs;
    }

    @Override
    @Transactional
    public RefreshToken createRefreshToken(User user) {
        logger.debug("Creating refresh token for user: {}", user.getEmail());
        
        try {
            // First, delete any existing refresh tokens for this user
            refreshTokenRepository.deleteByUser(user);
            // Flush to ensure the delete is committed
            entityManager.flush();
            logger.debug("Deleted existing refresh tokens for user: {}", user.getEmail());
            
            // Create new refresh token
            RefreshToken refreshToken = new RefreshToken();
            refreshToken.setUser(user);
            refreshToken.setToken(UUID.randomUUID().toString());
            refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
            
            // Save the new refresh token
            RefreshToken savedToken = refreshTokenRepository.save(refreshToken);
            logger.debug("Successfully created new refresh token for user: {}", user.getEmail());
            return savedToken;
        } catch (Exception e) {
            logger.error("Failed to create refresh token for user: {}", user.getEmail(), e);
            throw new RuntimeException("Failed to create refresh token: " + e.getMessage(), e);
        }
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        logger.debug("Verifying refresh token expiration for token: {}", token.getToken());
        
        if (token == null) {
            logger.error("Refresh token is null");
            throw new IllegalArgumentException("Refresh token is null");
        }
        
        if (token.getExpiryDate() == null) {
            logger.error("Refresh token expiry date is null for token: {}", token.getToken());
            throw new IllegalArgumentException("Refresh token expiry date is null");
        }
        
        if (token.getExpiryDate().isBefore(Instant.now())) {
            logger.error("Refresh token expired at {} for token: {}", token.getExpiryDate(), token.getToken());
            refreshTokenRepository.delete(token);
            throw new IllegalArgumentException("Refresh token has expired. Please make a new sign-in request");
        }
        
        logger.debug("Refresh token is valid and not expired for token: {}", token.getToken());
        return token;
    }

    @Override
    @Transactional
    public void deleteRefreshToken(String token) {
        refreshTokenRepository.findByToken(token).ifPresent(refreshTokenRepository::delete);
    }

    @Override
    @Transactional
    public void deleteRefreshTokensByUser(User user) {
        refreshTokenRepository.deleteByUser(user);
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        logger.debug("Finding refresh token: {}", token);
        if (token == null || token.trim().isEmpty()) {
            logger.error("Token is null or empty");
            return Optional.empty();
        }
        return refreshTokenRepository.findByToken(token);
    }
} 