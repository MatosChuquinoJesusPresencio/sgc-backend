package com.condominios.sgc.infrastructure.util;

import com.condominios.sgc.domain.auxiliar.TipoToken;
import com.condominios.sgc.domain.exception.TokenException;
import com.condominios.sgc.domain.model.TokenModel;
import io.jsonwebtoken.Jwts;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtTokenFactory {

    private final SecretKey secretKey;
    private final long accessTokenExpiration;
    private final long refreshTokenExpiration;
    private final long recuerdameRefreshExpiration;

    public JwtTokenFactory(
            SecretKey jwtSecretKey,
            @Value("${jwt.access-token-expiration:1800000}") long accessTokenExpiration,
            @Value("${jwt.refresh-token-expiration:604800000}") long refreshTokenExpiration,
            @Value("${jwt.remember-me-refresh-expiration:2592000000}") long recuerdameRefreshExpiration) {
        this.secretKey = jwtSecretKey;
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
        this.recuerdameRefreshExpiration = recuerdameRefreshExpiration;
    }

    public TokenModel crearToken(TipoToken tipo, Long idUsuario, String correo, String rol, boolean recuerdame) {
        Instant now = Instant.now();
        Instant expiracion = now.plusMillis(duracionMs(tipo, recuerdame));

        String jwt;
        try {
            jwt = Jwts.builder()
                    .subject(idUsuario.toString())
                    .issuedAt(Date.from(now))
                    .expiration(Date.from(expiracion))
                    .claim("tipo", tipo.name())
                    .claim("correo", correo)
                    .claim("rol", rol)
                    .signWith(secretKey)
                    .compact();
        } catch (Exception e) {
            throw TokenException.errorGeneracion("fallo al firmar JWT", e);
        }

        return new TokenModel(tipo, jwt, expiracion, idUsuario);
    }

    private long duracionMs(TipoToken tipo, boolean recuerdame) {
        return switch (tipo) {
            case ACCESS -> accessTokenExpiration;
            case REFRESH -> recuerdame ? recuerdameRefreshExpiration : refreshTokenExpiration;
            default -> throw TokenException.tipoNoSoportado(tipo);
        };
    }
}
