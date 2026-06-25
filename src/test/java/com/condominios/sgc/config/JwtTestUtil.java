package com.condominios.sgc.config;

import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtTestUtil {

    private static final SecretKey SECRET_KEY;

    static {
        byte[] keyBytes = Base64.getDecoder().decode("VGVzdFNlY3JldEtleUZvckpXVDI1NkJpdHMhISEhISE=");
        SECRET_KEY = Keys.hmacShaKeyFor(keyBytes);
    }

    public static String accessToken(Long userId, String email, String rol) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .id(UUID.randomUUID().toString())
                .subject(userId.toString())
                .claim("email", email)
                .claim("rol", rol)
                .issuedAt(new Date(now))
                .expiration(new Date(now + 3_600_000))
                .signWith(SECRET_KEY)
                .compact();
    }

    public static String refreshToken(Long userId) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .id(UUID.randomUUID().toString())
                .subject(userId.toString())
                .issuedAt(new Date(now))
                .expiration(new Date(now + 7L * 24 * 3_600_000))
                .signWith(SECRET_KEY)
                .compact();
    }

    public static String cookie(String token) {
        return "access_token=" + token;
    }
}
