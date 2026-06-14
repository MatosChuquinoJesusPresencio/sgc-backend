package com.condominios.sgc.domain.model;

public class PaisModel {
    private Long id;
    private String nombre;
    private String codigoIso;
    private Long idMoneda;

    public PaisModel(Long id, String nombre, String codigoIso, Long idMoneda) {
        this.id = id;
        this.nombre = nombre;
        this.codigoIso = codigoIso;
        this.idMoneda = idMoneda;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getCodigoIso() { return codigoIso; }
    public Long getIdMoneda() { return idMoneda; }
}
