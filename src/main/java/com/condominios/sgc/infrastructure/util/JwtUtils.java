package com.condominios.sgc.infrastructure.util;

import org.springframework.security.oauth2.jwt.Jwt;

public final class JwtUtils {

    private JwtUtils() {}

    public static String getSubject(Jwt jwt) {
        return jwt.getSubject();
    }

    public static String getEmail(Jwt jwt) {
        return jwt.getClaimAsString("email");
    }
}
