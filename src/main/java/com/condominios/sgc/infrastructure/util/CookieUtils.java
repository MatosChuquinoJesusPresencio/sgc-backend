package com.condominios.sgc.infrastructure.util;

import org.springframework.http.ResponseCookie;

public final class CookieUtils {

    private static final String ACCESS_COOKIE = "access_token";
    private static final String REFRESH_COOKIE = "refresh_token";
    private static final String PATH = "/";
    private static final String REFRESH_PATH = "/api/auth/refresh";

    private CookieUtils() {}

    public static ResponseCookie crearCookieAccessToken(String token, long maxAgeMs) {
        return ResponseCookie.from(ACCESS_COOKIE, token)
            .httpOnly(true).secure(true).path(PATH)
            .maxAge(maxAgeMs / 1000).sameSite("None").build();
    }

    public static ResponseCookie crearCookieRefreshToken(String token, long maxAgeMs) {
        return ResponseCookie.from(REFRESH_COOKIE, token)
            .httpOnly(true).secure(true).path(REFRESH_PATH)
            .maxAge(maxAgeMs / 1000).sameSite("None").build();
    }

    public static ResponseCookie limpiarCookieAccessToken() {
        return ResponseCookie.from(ACCESS_COOKIE, "")
            .httpOnly(true).secure(true).path(PATH)
            .maxAge(0).build();
    }

    public static ResponseCookie limpiarCookieRefreshToken() {
        return ResponseCookie.from(REFRESH_COOKIE, "")
            .httpOnly(true).secure(true).path(REFRESH_PATH)
            .maxAge(0).build();
    }
}
