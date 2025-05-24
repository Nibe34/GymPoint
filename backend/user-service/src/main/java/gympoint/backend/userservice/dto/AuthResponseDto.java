package gympoint.backend.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDto {
    private String accessTokenCookie;
    private String refreshTokenCookie;
    private Long userId;

    public String getAccessTokenCookie() {
        return accessTokenCookie;
    }

    public String getRefreshTokenCookie() {
        return refreshTokenCookie;
    }

    public Long getUserId() {
        return userId;
    }
} 