package gympoint.backend.authservice.service.impl;

import gympoint.backend.authservice.service.CookieService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
public class CookieServiceImpl implements CookieService {

    @Value("${app.cookie.domain}")
    private String cookieDomain;

    @Value("${app.cookie.secure}")
    private boolean cookieSecure;

    @Override
    public ResponseCookie createAccessTokenCookie(String token) {
        return ResponseCookie.from("access_token", token)
                .httpOnly(true)
                .secure(cookieSecure)
                .domain(cookieDomain)
                .path("/")
                .maxAge(3600) // 1 hour
                .build();
    }

    @Override
    public ResponseCookie createRefreshTokenCookie(String token) {
        return ResponseCookie.from("refresh_token", token)
                .httpOnly(true)
                .secure(cookieSecure)
                .domain(cookieDomain)
                .path("/")
                .maxAge(604800) // 7 days
                .build();
    }
} 