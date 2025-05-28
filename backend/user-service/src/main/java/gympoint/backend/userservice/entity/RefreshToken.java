package gympoint.backend.userservice.entity;

import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@Table(name = "refresh_token", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id"}),
    @UniqueConstraint(columnNames = {"token"})
})
public class RefreshToken {
    private static final Logger logger = LoggerFactory.getLogger(RefreshToken.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private Instant expiryDate;

    @PrePersist
    @PreUpdate
    public void validate() {
        if (user == null) {
            logger.error("Refresh token validation failed: user is null");
            throw new IllegalStateException("User cannot be null");
        }
        if (token == null || token.trim().isEmpty()) {
            logger.error("Refresh token validation failed: token is null or empty");
            throw new IllegalStateException("Token cannot be null or empty");
        }
        if (expiryDate == null) {
            logger.error("Refresh token validation failed: expiry date is null");
            throw new IllegalStateException("Expiry date cannot be null");
        }
    }
} 