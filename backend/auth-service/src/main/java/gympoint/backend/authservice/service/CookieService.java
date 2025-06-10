package gympoint.backend.authservice.service;

import org.springframework.http.ResponseCookie;

public interface CookieService {
    ResponseCookie createAccessTokenCookie(String token);
    ResponseCookie createRefreshTokenCookie(String token);
} 