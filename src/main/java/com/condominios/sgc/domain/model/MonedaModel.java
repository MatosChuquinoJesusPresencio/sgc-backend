package com.condominios.sgc.domain.model;

public class MonedaModel {
    private Long id;
    private String nombre;
    private String codigo;
    private String simbolo;

    public MonedaModel(Long id, String nombre, String codigo, String simbolo) {
        this.id = id;
        this.nombre = nombre;
        this.codigo = codigo;
        this.simbolo = simbolo;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getCodigo() { return codigo; }
    public String getSimbolo() { return simbolo; }
}
