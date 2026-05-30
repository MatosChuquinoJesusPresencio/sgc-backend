package com.condominios.sgc.domain.model;

import java.time.Instant;
import java.util.UUID;

public class VerificacionTokenModel {

    private String id;
    private String usuarioId;
    private String nuevoCorreo;
    private String token;
    private Instant expiracion;
    private Boolean usado;

    public VerificacionTokenModel(String id, String usuarioId, String nuevoCorreo, String token, Instant expiracion) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.nuevoCorreo = nuevoCorreo;
        this.token = token;
        this.expiracion = expiracion;
        this.usado = false;
    }

    public static VerificacionTokenModel crear(String usuarioId, String nuevoCorreo) {
        return new VerificacionTokenModel(
            UUID.randomUUID().toString(),
            usuarioId,
            nuevoCorreo,
            UUID.randomUUID().toString(),
            Instant.now().plusSeconds(3600)
        );
    }

    public String getId() { return id; }
    public String getUsuarioId() { return usuarioId; }
    public String getNuevoCorreo() { return nuevoCorreo; }
    public String getToken() { return token; }
    public Instant getExpiracion() { return expiracion; }
    public Boolean isUsado() { return usado; }

    public void cambiarUsado(Boolean usado) { this.usado = usado; }
}
