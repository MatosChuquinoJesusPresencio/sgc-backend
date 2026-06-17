package com.condominios.sgc.infrastructure.util;

import com.condominios.sgc.domain.auxiliar.TipoToken;
import com.condominios.sgc.domain.exception.TokenException;
import com.condominios.sgc.domain.model.TokenModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.time.Instant;
import java.util.UUID;

@Component
public class TokenFactory {

    private final long resetPasswordTokenExpiration;
    private final long verifyEmailTokenExpiration;

    public TokenFactory(
            @Value("${jwt.reset-password-token-expiration:900000}") long resetPasswordTokenExpiration,
            @Value("${jwt.verify-email-token-expiration:900000}") long verifyEmailTokenExpiration) {
        this.resetPasswordTokenExpiration = resetPasswordTokenExpiration;
        this.verifyEmailTokenExpiration = verifyEmailTokenExpiration;
    }

    public TokenModel crearToken(TipoToken tipo, Long idUsuario) {
        String tokenStr = UUID.randomUUID().toString();
        Instant expiracion = Instant.now().plusMillis(duracionMs(tipo));
        return new TokenModel(tipo, tokenStr, expiracion, idUsuario);
    }

    private long duracionMs(TipoToken tipo) {
        return switch (tipo) {
            case VERIFICACION -> verifyEmailTokenExpiration;
            case REESTABLECIMIENTO -> resetPasswordTokenExpiration;
            default -> throw TokenException.tipoNoSoportado(tipo);
        };
    }
}
