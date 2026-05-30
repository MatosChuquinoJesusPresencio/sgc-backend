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
        this.id = id;
        asignarDatos(nombre, condominioId);
    }

    public TorreModel(
        String nombre,
        Long condominioId
    ) {
        this(null, nombre, condominioId);
    }

    private void asignarDatos(String nombre, Long condominioId) {
        this.nombre = requerirNoVacio(nombre, TorreException::nombreObligatorio);
        this.condominioId = requerirNoNulo(condominioId, TorreException::condominioIdObligatorio);
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public Long getCondominioId() { return condominioId; }

    public void actualizar(String nombre, Long condominioId) {
        asignarDatos(nombre, condominioId);
    }
}
