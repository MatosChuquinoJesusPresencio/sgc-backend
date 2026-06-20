package com.condominios.sgc.infrastructure.security;

import com.condominios.sgc.application.port.out.service.JwtServicePort;
import com.condominios.sgc.domain.type.Rol;
import com.condominios.sgc.infrastructure.config.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtProviderAdapter implements JwtServicePort {

    private final SecretKey key;
    private final JwtProperties props;

    public JwtProviderAdapter(JwtProperties props) {
        this.props = props;
        byte[] keyBytes = Base64.getDecoder().decode(props.getSecret());
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public String generarTokenAcceso(Long usuarioId, String correo, Rol rol) {
        return Jwts.builder()
                .subject(usuarioId.toString())
                .claim("correo", correo)
                .claim("rol", rol.name())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + props.getAccessTokenExpiration()))
                .signWith(key)
                .compact();
    }

    @Override
    public String generarTokenRefresco(Long usuarioId, boolean recuerdame) {
        var expiracion = recuerdame ? props.getRememberMeRefreshExpiration()
                                    : props.getRefreshTokenExpiration();
        return Jwts.builder()
                .subject(usuarioId.toString())
                .claim("recuerdame", recuerdame)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiracion))
                .signWith(key)
                .compact();
    }

    @Override
    public String generarTokenVerificacion(Long usuarioId) {
        return Jwts.builder()
                .subject(usuarioId.toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + props.getVerifyEmailTokenExpiration()))
                .signWith(key)
                .compact();
    }

    @Override
    public String generarTokenRestablecimiento(Long usuarioId) {
        return Jwts.builder()
                .subject(usuarioId.toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + props.getResetPasswordTokenExpiration()))
                .signWith(key)
                .compact();
    }

    @Override
    public Claims validarToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    @Override
    public Long obtenerUsuarioId(String token) {
        return Long.parseLong(validarToken(token).getSubject());
    }

    @Override
    public Instant obtenerExpiracion(String token) {
        return validarToken(token).getExpiration().toInstant();
    }
}
