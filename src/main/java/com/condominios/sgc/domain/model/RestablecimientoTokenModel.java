package com.condominios.sgc.domain.model;

import java.time.Instant;

public class RestablecimientoTokenModel {

    private String id;
    private String usuarioId;
    private String token;
    private Instant expiracion;
    private Boolean usado = false;

    public RestablecimientoTokenModel(String id, String usuarioId, String token, Instant expiracion) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.token = token;
        this.expiracion = expiracion;
    }

    public String getId() { return id; }
    public String getUsuarioId() { return usuarioId; }
    public String getToken() { return token; }
    public Instant getExpiracion() { return expiracion; }
    public Boolean isUsado() { return usado; }
    public void setUsado(Boolean usado) { this.usado = usado; }
}
