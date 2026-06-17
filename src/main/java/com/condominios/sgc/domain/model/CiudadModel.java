package com.condominios.sgc.domain.model;

public class CiudadModel {
    private Long id;
    private String nombre;
    private Long idPais;

    public CiudadModel(Long id, String nombre, Long idPais) {
        this.id = id;
        this.nombre = nombre;
        this.idPais = idPais;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public Long getIdPais() { return idPais; }
}
