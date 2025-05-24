package gympoint.backend.userservice.service.impl;

import gympoint.backend.userservice.service.CookieService;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
public class CookieServiceImpl implements CookieService {

    @Override
    public ResponseCookie createAccessTokenCookie(String token) {
        return ResponseCookie.from("access_token", token)
                .httpOnly(true)
                .secure(false) // Set to true in production
                .path("/")
                .sameSite("Lax")
                .maxAge(3600) // 1 hour
                .build();
    }

    @Override
    public ResponseCookie createRefreshTokenCookie(String token) {
        return ResponseCookie.from("refresh_token", token)
                .httpOnly(true)
                .secure(false) // Set to true in production
                .path("/")
                .sameSite("Lax")
                .maxAge(604800) // 7 days
                .build();
    }
} 