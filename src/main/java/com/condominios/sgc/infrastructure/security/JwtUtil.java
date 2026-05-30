package com.condominios.sgc.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

public class JwtUtil {

    private final SecretKey secretKey;
    private final long accessTokenExpiration;
    private final long refreshTokenExpiration;

    public JwtUtil(String secretBase64, long accessTokenExpiration, long refreshTokenExpiration) {
        byte[] keyBytes = Base64.getDecoder().decode(secretBase64);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
    }

    public String generateAccessToken(String userId, String email, String rol) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
            .subject(userId)
            .claim("email", email)
            .claim("rol", rol)
            .issuedAt(new Date(now))
            .expiration(new Date(now + accessTokenExpiration))
            .signWith(secretKey)
            .compact();
    }

    public String generateRefreshToken(String userId) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
            .subject(userId)
            .issuedAt(new Date(now))
            .expiration(new Date(now + refreshTokenExpiration))
            .signWith(secretKey)
            .compact();
    }

    public Claims validateToken(String token) {
        try {
            return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        } catch (JwtException e) {
            throw new JwtException("Token invalido: " + e.getMessage());
        }
    }

    public long getAccessTokenExpiration() {
        return accessTokenExpiration;
    }
}
