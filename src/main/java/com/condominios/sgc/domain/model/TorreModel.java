package com.condominios.sgc.domain.model;

import com.condominios.sgc.domain.exception.TorreException;
import static com.condominios.sgc.domain.util.ValidacionUtil.*;

public class TorreModel {
    private Long id;
    private String nombre;
    private Long idCondominio;

    public TorreModel(Long id, String nombre, Long idCondominio) {
        this.id = id;
        this.nombre = nombre;
        this.idCondominio = idCondominio;
    }

    public TorreModel(String nombre, Long idCondominio) {
        this.id = null;
        this.nombre = requerido(nombre, TorreException::nombreRequerido);
        this.idCondominio = noNulo(idCondominio, TorreException::condominioRequerido);
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public Long getIdCondominio() { return idCondominio; }

    public void actualizarNombre(String nombre) {
        this.nombre = requerido(nombre, TorreException::nombreRequerido);
    }
}
