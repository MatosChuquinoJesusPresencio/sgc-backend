package com.condominios.sgc.infrastructure.security;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.condominios.sgc.domain.model.UsuarioModel;
import com.condominios.sgc.domain.port.UsuarioPort;
import com.condominios.sgc.infrastructure.util.JwtUtil;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Value("${cors.allowed-origins:*}")
    private String allowedOrigins;

    @Bean
    @Order(1)
    public SecurityFilterChain publicEndpoints(HttpSecurity http) throws Exception {
        http
            .securityMatcher("/api/auth/login", "/api/auth/refresh",
                "/api/auth/forgot-password", "/api/auth/reset-password",
                "/api/auth/verificar-email",
                "/api/health", "/api/health/**",
                "/v3/api-docs", "/v3/api-docs/**",
                "/swagger-ui/**", "/swagger-ui.html")
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain protectedEndpoints(HttpSecurity http, UsuarioPort usuarioPort, JwtDecoder jwtDecoder) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
            .oauth2ResourceServer(oauth2 -> oauth2
                .bearerTokenResolver(cookieBearerTokenResolver())
                .jwt(jwt -> jwt
                    .decoder(jwtDecoder)
                    .jwtAuthenticationConverter(jwtAuthenticationConverter(usuarioPort))
                )
            );
        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder(JwtUtil jwtUtil) {
        return token -> {
            try {
                var claims = jwtUtil.validateToken(token);
                return Jwt.withTokenValue(token)
                    .subject(claims.getSubject())
                    .claim("email", claims.get("email"))
                    .claim("rol", claims.get("rol"))
                    .issuedAt(claims.getIssuedAt() != null
                        ? claims.getIssuedAt().toInstant() : Instant.now())
                    .expiresAt(claims.getExpiration() != null
                        ? claims.getExpiration().toInstant() : Instant.now())
                    .header("alg", "HS256")
                    .build();
            } catch (JwtException e) {
                throw new JwtException("Token invalido: " + e.getMessage());
            }
        };
    }

    private JwtAuthenticationConverter jwtAuthenticationConverter(UsuarioPort usuarioPort) {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            String userId = jwt.getSubject();
            return usuarioPort.findById(Long.valueOf(userId))
                .filter(UsuarioModel::isActivo)
                .<Collection<GrantedAuthority>>map(usuario ->
                    List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().name())))
                .orElseThrow(() -> new AccessDeniedException("Usuario inactivo o no encontrado"));
        });
        return converter;
    }

    @Bean
    public CookieBearerTokenResolver cookieBearerTokenResolver() {
        return new CookieBearerTokenResolver();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(List.of(allowedOrigins));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
