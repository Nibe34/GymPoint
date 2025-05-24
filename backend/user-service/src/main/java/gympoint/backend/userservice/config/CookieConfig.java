package gympoint.backend.userservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Configuration
public class CookieConfig {

    @Bean
    public ResponseCookie.ResponseCookieBuilder accessTokenCookieBuilder() {
        return ResponseCookie.from("access_token", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite("None")
                .maxAge(3600); // 1 hour
    }

    @Bean
    public ResponseCookie.ResponseCookieBuilder refreshTokenCookieBuilder() {
        return ResponseCookie.from("refresh_token", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite("None")
                .maxAge(604800); // 7 days
    }
} 