package com.condominios.sgc.domain.model;

import static com.condominios.sgc.domain.util.ValidacionUtil.*;

import com.condominios.sgc.domain.exception.TorreException;

public class TorreModel {
    private Long id;
    private String nombre;
    private Long condominioId;

    public TorreModel(
        Long id,
        String nombre,
        Long condominioId
    ) {
        this(nombre, condominioId);
        this.id = id;
    }

    public TorreModel(
        String nombre,
        Long condominioId
    ) {
        this.nombre = requerirNoVacio(nombre, TorreException::nombreObligatorio);
        this.condominioId = requerirNoNulo(condominioId, TorreException::condominioIdObligatorio);
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public Long getCondominioId() { return condominioId; }

    public void actualizarDatos(String nombre) {
        this.nombre = requerirNoVacio(nombre, TorreException::nombreObligatorio);
    }
}
