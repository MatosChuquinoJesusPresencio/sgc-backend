package com.condominios.sgc.infrastructure.security;

import org.springframework.http.ResponseCookie;

public final class CookieUtil {

    public static final String JWT_COOKIE_NAME = "jwt";

    private CookieUtil() {}

    public static ResponseCookie crearCookieJwt(String token, long maxAge) {
        return ResponseCookie.from(JWT_COOKIE_NAME, token)
            .httpOnly(true)
            .secure(true)
            .path("/")
            .maxAge(maxAge)
            .sameSite("Lax")
            .build();
    }

    public static ResponseCookie limpiarCookieJwt() {
        return crearCookieJwt("", 0);
    }
}
