package com.condominios.sgc.infrastructure.util;

import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {

    private final String accessTokenName;
    private final String refreshTokenName;
    private final String path;
    private final boolean secure;

    public CookieUtil(
            @Value("${cookie.access-token-name}") String accessTokenName,
            @Value("${cookie.refresh-token-name}") String refreshTokenName,
            @Value("${cookie.path}") String path,
            @Value("${cookie.secure}") boolean secure) {
        this.accessTokenName = accessTokenName;
        this.refreshTokenName = refreshTokenName;
        this.path = path;
        this.secure = secure;
    }

    public Cookie crearAccessTokenCookie(String token, long maxAgeMs) {
        return build(accessTokenName, token, (int) (maxAgeMs / 1000));
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
