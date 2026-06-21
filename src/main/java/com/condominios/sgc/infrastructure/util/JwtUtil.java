package com.condominios.sgc.infrastructure.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    private final SecretKey secretKey;
    private final long accessTokenExpiration;
    private final long refreshTokenExpiration;
    private final long rememberMeRefreshExpiration;
    private final long resetPasswordTokenExpiration;
    private final long verifyEmailTokenExpiration;

    public JwtUtil(
            @Value("${jwt.secret}") String secretBase64,
            @Value("${jwt.access-token-expiration}") long accessTokenExpiration,
            @Value("${jwt.refresh-token-expiration}") long refreshTokenExpiration,
            @Value("${jwt.remember-me-refresh-expiration}") long rememberMeRefreshExpiration,
            @Value("${jwt.reset-password-token-expiration}") long resetPasswordTokenExpiration,
            @Value("${jwt.verify-email-token-expiration}") long verifyEmailTokenExpiration) {
        byte[] keyBytes = Base64.getDecoder().decode(secretBase64);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
        this.rememberMeRefreshExpiration = rememberMeRefreshExpiration;
        this.resetPasswordTokenExpiration = resetPasswordTokenExpiration;
        this.verifyEmailTokenExpiration = verifyEmailTokenExpiration;
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
        return generateRefreshToken(userId, refreshTokenExpiration);
    }

    public String generateRefreshToken(String userId, long expiration) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
            .subject(userId)
            .issuedAt(new Date(now))
            .expiration(new Date(now + expiration))
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
        } catch (io.jsonwebtoken.JwtException e) {
            throw new JwtException("Token invalido: " + e.getMessage());
        }
    }

    public long getAccessTokenExpiration() {
        return accessTokenExpiration;
    }

    public long getRefreshTokenExpiration() {
        return refreshTokenExpiration;
    }

    public long getRememberMeRefreshExpiration() {
        return rememberMeRefreshExpiration;
    }

    public long getResetPasswordTokenExpiration() {
        return resetPasswordTokenExpiration;
    }

    public long getVerifyEmailTokenExpiration() {
        return verifyEmailTokenExpiration;
    }

    public String generateToken(String userId, long expirationMs) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
            .subject(userId)
            .issuedAt(new Date(now))
            .expiration(new Date(now + expirationMs))
            .signWith(secretKey)
            .compact();
    }
}
