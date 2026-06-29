package com.condominios.sgc.infrastructure.config;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.condominios.sgc.infrastructure.util.JwtUtil;
import com.condominios.sgc.infrastructure.security.CookieBearerTokenResolver;

@Configuration
@EnableRetry
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Primary
    public CorsConfigurationSource corsConfigurationSource(
            @Value("${cors.allowed-origins}") List<String> allowedOrigins) {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(allowedOrigins);
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public CookieBearerTokenResolver cookieBearerTokenResolver(
            @Value("${cookie.access-token-name}") String accessCookieName) {
        return new CookieBearerTokenResolver(accessCookieName);
    }

    @Bean
    public JwtDecoder jwtDecoder(JwtUtil jwtUtil) {
        return token -> {
            try {
                var jws = jwtUtil.validateToken(token);
                var payload = jws.getPayload();
                var header = jws.getHeader();
                Instant issuedAt = payload.getIssuedAt() != null
                    ? payload.getIssuedAt().toInstant() : Instant.EPOCH;
                Instant expiresAt = payload.getExpiration() != null
                    ? payload.getExpiration().toInstant() : Instant.now().plusSeconds(60);
                return Jwt.withTokenValue(token)
                    .subject(payload.getSubject())
                    .claim("email", payload.get("email"))
                    .claim("rol", payload.get("rol"))
                    .issuedAt(issuedAt)
                    .expiresAt(expiresAt)
                    .header("alg", header.getAlgorithm())
                    .build();
            } catch (JwtException e) {
                throw new JwtException("Token inválido: " + e.getMessage());
            }
        };
    }
}
