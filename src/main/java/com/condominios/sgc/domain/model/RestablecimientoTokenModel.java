package com.condominios.sgc.domain.model;

import java.time.Instant;
import java.util.UUID;

public class RestablecimientoTokenModel {

    private String id;
    private String usuarioId;
    private String token;
    private Instant expiracion;
    private Boolean usado;

    public RestablecimientoTokenModel(String id, String usuarioId, String token, Instant expiracion) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.token = token;
        this.expiracion = expiracion;
        this.usado = false;
    }

    public static RestablecimientoTokenModel crear(String usuarioId) {
        return new RestablecimientoTokenModel(
            UUID.randomUUID().toString(),
            usuarioId,
            UUID.randomUUID().toString(),
            Instant.now().plusSeconds(3600)
        );
    }

    public String getId() { return id; }
    public String getUsuarioId() { return usuarioId; }
    public String getToken() { return token; }
    public Instant getExpiracion() { return expiracion; }
    public Boolean isUsado() { return usado; }

    public void cambiarUso(Boolean usado) { this.usado = usado; }
}
