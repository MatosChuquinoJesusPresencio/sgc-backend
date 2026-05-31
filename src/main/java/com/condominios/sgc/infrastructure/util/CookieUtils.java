package com.condominios.sgc.infrastructure.util;

import org.springframework.http.ResponseCookie;

public final class CookieUtils {

    private static final String COOKIE_NAME = "access_token";
    private static final String PATH = "/";

    private CookieUtils() {}

    public static ResponseCookie crearCookieJwt(String token, long maxAge) {
        return ResponseCookie.from(COOKIE_NAME, token)
            .httpOnly(true)
            .secure(true)
            .path(PATH)
            .maxAge(maxAge)
            .sameSite("Strict")
            .build();
    }

    public static ResponseCookie limpiarCookieJwt() {
        return ResponseCookie.from(COOKIE_NAME, "")
            .httpOnly(true)
            .secure(true)
            .path(PATH)
            .maxAge(0)
            .build();
    }
}
