package com.condominios.sgc.infrastructure.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class CookieUtil {

    private final String accessTokenName;
    private final String refreshTokenName;
    private final String path;
    private final boolean secure;
    private final long accessTokenExpiration;
    private final long refreshTokenExpiration;
    private final long recuerdameRefreshExpiration;

    public CookieUtil(
            @Value("${cookie.access-token-name:access_token}") String accessTokenName,
            @Value("${cookie.refresh-token-name:refresh_token}") String refreshTokenName,
            @Value("${cookie.path:/}") String path,
            @Value("${cookie.secure:true}") boolean secure,
            @Value("${jwt.access-token-expiration}") long accessTokenExpiration,
            @Value("${jwt.refresh-token-expiration}") long refreshTokenExpiration,
            @Value("${jwt.remember-me-refresh-expiration}") long recuerdameRefreshExpiration) {
        this.accessTokenName = accessTokenName;
        this.refreshTokenName = refreshTokenName;
        this.path = path;
        this.secure = secure;
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
        this.recuerdameRefreshExpiration = recuerdameRefreshExpiration;
    }

    public Cookie crearAccessTokenCookie(String token) {
        return build(accessTokenName, token, (int) (accessTokenExpiration / 1000));
    }

    public Cookie crearAccessTokenCookie(String token, long maxAgeMs) {
        return build(accessTokenName, token, (int) (maxAgeMs / 1000));
    }

    public Cookie crearRefreshTokenCookie(String token, boolean recuerdame) {
        long maxAgeMs = recuerdame ? recuerdameRefreshExpiration : refreshTokenExpiration;
        return build(refreshTokenName, token, (int) (maxAgeMs / 1000));
    }

    public Cookie crearRefreshTokenCookie(String token, long maxAgeMs) {
        return build(refreshTokenName, token, (int) (maxAgeMs / 1000));
    }

    public Cookie eliminarAccessTokenCookie() {
        return build(accessTokenName, "", 0);
    }

    public Cookie eliminarRefreshTokenCookie() {
        return build(refreshTokenName, "", 0);
    }

    public String getAccessTokenName() {
        return accessTokenName;
    }

    public String getRefreshTokenName() {
        return refreshTokenName;
    }

    public Optional<String> readCookie(HttpServletRequest request, String name) {
        var cookies = request.getCookies();
        if (cookies == null) return Optional.empty();
        for (var cookie : cookies) {
            if (cookie.getName().equals(name)) return Optional.of(cookie.getValue());
        }
        return Optional.empty();
    }

    private Cookie build(String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setSecure(secure);
        cookie.setPath(path);
        cookie.setMaxAge(maxAge);
        cookie.setAttribute("SameSite", "None");
        return cookie;
    }
}
