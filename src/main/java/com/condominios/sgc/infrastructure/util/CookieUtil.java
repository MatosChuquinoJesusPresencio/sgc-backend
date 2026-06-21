package com.condominios.sgc.infrastructure.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class CookieUtil {

    private final String accessCookieName;
    private final String refreshCookieName;
    private final String path;
    private final boolean secure;

    public CookieUtil(
            @Value("${cookie.access-token-name}") String accessCookieName,
            @Value("${cookie.refresh-token-name}") String refreshCookieName,
            @Value("${cookie.path}") String path,
            @Value("${cookie.secure}") boolean secure) {
        this.accessCookieName = accessCookieName;
        this.refreshCookieName = refreshCookieName;
        this.path = path;
        this.secure = secure;
    }

    public ResponseCookie crearCookieAccessToken(String token, long maxAgeMs) {
        return ResponseCookie.from(accessCookieName, token)
            .httpOnly(true).secure(secure).path(path)
            .maxAge(maxAgeMs / 1000).sameSite("None").build();
    }

    public ResponseCookie crearCookieRefreshToken(String token, long maxAgeMs) {
        return ResponseCookie.from(refreshCookieName, token)
            .httpOnly(true).secure(secure).path(path)
            .maxAge(maxAgeMs / 1000).sameSite("None").build();
    }

    public ResponseCookie limpiarCookieAccessToken() {
        return ResponseCookie.from(accessCookieName, "")
            .httpOnly(true).secure(secure).path(path)
            .maxAge(0).build();
    }

    public ResponseCookie limpiarCookieRefreshToken() {
        return ResponseCookie.from(refreshCookieName, "")
            .httpOnly(true).secure(secure).path(path)
            .maxAge(0).build();
    }

    public String extraerRefreshToken(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(refreshCookieName)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
