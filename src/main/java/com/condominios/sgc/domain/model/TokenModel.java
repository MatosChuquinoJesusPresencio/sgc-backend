package com.condominios.sgc.domain.model;

import java.time.Instant;

import com.condominios.sgc.domain.type.TipoToken;
import com.condominios.sgc.domain.shared.exception.TokenException;

import static com.condominios.sgc.domain.util.ValidacionUtil.*;

public class TokenModel {
    private Long id;
    private TipoToken tipo;
    private String token;
    private Instant expiracion;
    private Long idUsuario;

    public TokenModel(Long id, TipoToken tipo, String token, 
            Instant expiracion, Long idUsuario) {
        this.id = id;
        this.tipo = tipo;
        this.token = token;
        this.expiracion = expiracion;
        this.idUsuario = idUsuario;
    }

    public TokenModel(TipoToken tipo, String token, Instant expiracion, Long idUsuario) {
        this.id = null;
        this.tipo = noNulo(tipo, TokenException::tipoRequerido);
        this.token = requerido(token, TokenException::tokenRequerido);
        this.expiracion = noNulo(expiracion, TokenException::expiracionRequerida);
        this.idUsuario = noNulo(idUsuario, TokenException::usuarioRequerido);
    }

    public Long getId() { return id; }
    public TipoToken getTipo() { return tipo; }
    public String getToken() { return token; }
    public Instant getExpiracion() { return expiracion; }
    public Long getIdUsuario() { return idUsuario; }

    public void validarExpiracion() {
        if (this.expiracion != null && this.expiracion.isBefore(Instant.now()))
            throw TokenException.tokenExpirado();
    }
}
