package gympoint.backend.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDto {
    private String accessTokenCookie;
    private String refreshTokenCookie;
    private Long userId;
    private String accessToken;
    private String refreshToken;
} 