package gympoint.backend.authservice.service.impl;

import gympoint.backend.authservice.entity.RefreshToken;
import gympoint.backend.authservice.entity.User;
import gympoint.backend.authservice.repository.RefreshTokenRepository;
import gympoint.backend.authservice.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private static final Logger logger = LoggerFactory.getLogger(RefreshTokenServiceImpl.class);

    private final RefreshTokenRepository refreshTokenRepository;
    
    @PersistenceContext
    private EntityManager entityManager;

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
            refreshToken.setExpiryDate(LocalDateTime.now().plusDays(7));
            
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
        if (token.getExpiryDate().isBefore(LocalDateTime.now())) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token was expired. Please make a new signin request");
        }
        return token;
    }

    @Override
    public void deleteRefreshToken(User user) {
        refreshTokenRepository.deleteByUser(user);
    }

    @Override
    public RefreshToken findByToken(String token) {
        return refreshTokenRepository.findByToken(token)
            .orElseThrow(() -> new RuntimeException("Refresh token not found"));
    }

    @Override
    public void deleteRefreshTokensByUser(User user) {
        refreshTokenRepository.deleteByUser(user);
    }

    @Override
    public Optional<RefreshToken> findByTokenOptional(String token) {
        return refreshTokenRepository.findByToken(token);
    }
} 