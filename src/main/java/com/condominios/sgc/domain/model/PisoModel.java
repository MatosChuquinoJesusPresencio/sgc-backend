package com.condominios.sgc.domain.model;

import static com.condominios.sgc.domain.util.ValidacionUtil.*;

import com.condominios.sgc.domain.exception.PisoException;

public class PisoModel {
    private Long id;
    private Integer numero;
    private Long torreId;

    public PisoModel(
        Long id, 
        Integer numero, 
        Long torreId
    ) {
        this.id = id;
        asignarDatos(numero, torreId);
    }

    public PisoModel(
        Integer numero, 
        Long torreId
    ) {
        this(null, numero, torreId);
    }

    private void asignarDatos(Integer numero, Long torreId) {
        this.numero = requerirPositivo(numero, PisoException::numeroInvalido);
        this.torreId = requerirNoNulo(torreId, PisoException::torreIdObligatorio);
    }

    public Long getId() { return id; }
    public Integer getNumero() { return numero; }
    public Long getTorreId() { return torreId; }

    public void actualizar(Integer numero, Long torreId) {
        asignarDatos(numero, torreId);
    }
}
