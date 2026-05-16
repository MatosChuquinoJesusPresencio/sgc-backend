package com.condominios.sgc.infrastructure.util;

import org.springframework.http.ResponseCookie;

public final class CookieUtils {

    public static final String JWT_COOKIE_NAME = "jwt";

    private CookieUtils() {}

    public static ResponseCookie crearCookieJwt(String token, long maxAge) {
        return ResponseCookie.from(JWT_COOKIE_NAME, token)
            .httpOnly(true)
            .path("/")
            .maxAge(maxAge)
            .sameSite("Lax")
            .build();
    }

    public static ResponseCookie limpiarCookieJwt() {
        return crearCookieJwt("", 0);
    }
}
