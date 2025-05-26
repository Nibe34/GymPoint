package gympoint.backend.workoutservice.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

    @Value("${app.jwtSecret}")
    private String secretKey;

    @Value("${app.jwtExpirationInMs}")
    private long jwtExpiration;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }

    public Long extractUserId(String token) {
        return extractClaim(token, claims -> claims.get("userId", Long.class));
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS384)
                .compact();
    }

    public boolean isTokenValid(String token) {
        try {
            logger.debug("Validating token: {}", token);
            logger.debug("Using secret key: {}", secretKey);
            logger.debug("Token parts count: {}", token.split("\\.").length);
            logger.debug("Token parts: {}", String.join(", ", token.split("\\.")));
            logger.debug("Token length: {}", token.length());
            logger.debug("Token contains spaces: {}", token.contains(" "));
            logger.debug("Token contains newlines: {}", token.contains("\n"));
            logger.debug("Token contains carriage returns: {}", token.contains("\r"));
            
            var parser = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build();
            
            logger.debug("Parsing token...");
            var claims = parser.parseClaimsJws(token);
            logger.debug("Token parsed successfully. Claims: {}", claims.getBody());
            
            return true;
        } catch (Exception e) {
            logger.error("Token validation failed: {}", e.getMessage(), e);
            logger.error("Token that caused the error: {}", token);
            return false;
        }
    }

    private Claims extractAllClaims(String token) {
        try {
            logger.debug("Extracting claims from token: {}", token);
            logger.debug("Token parts count: {}", token.split("\\.").length);
            logger.debug("Token parts: {}", String.join(", ", token.split("\\.")));
            logger.debug("Token length: {}", token.length());
            logger.debug("Token contains spaces: {}", token.contains(" "));
            logger.debug("Token contains newlines: {}", token.contains("\n"));
            logger.debug("Token contains carriage returns: {}", token.contains("\r"));
            
            var parser = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build();
            
            logger.debug("Parsing token...");
            var claims = parser.parseClaimsJws(token);
            logger.debug("Token parsed successfully. Claims: {}", claims.getBody());
            
            return claims.getBody();
        } catch (Exception e) {
            logger.error("Failed to extract claims: {}", e.getMessage(), e);
            logger.error("Token that caused the error: {}", token);
            throw e;
        }
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
} 