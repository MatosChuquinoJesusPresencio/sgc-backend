package com.condominios.sgc.domain.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.condominios.sgc.domain.shared.exception.PisoException;
import com.condominios.sgc.domain.shared.exception.TorreException;
import static com.condominios.sgc.domain.util.ValidacionUtil.*;

public class TorreModel {
    private Long id;
    private String nombre;
    private List<PisoModel> pisos;

    public TorreModel(Long id, String nombre, List<PisoModel> pisos) {
        this.id = id;
        this.nombre = nombre;
        this.pisos = new ArrayList<>(pisos);
    }

    public TorreModel(String nombre) {
        this.id = null;
        this.nombre = requerido(nombre, TorreException::nombreRequerido);
        this.pisos = new ArrayList<>();
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }

    public List<PisoModel> getPisos() {
        return Collections.unmodifiableList(pisos);
    }

    public void actualizarNombre(String nombre) {
        this.nombre = requerido(nombre, TorreException::nombreRequerido);
    }

    public PisoModel agregarPiso(Integer numero) {
        positivo(numero, PisoException::numeroRequerido);
        var piso = new PisoModel(numero);
        pisos.add(piso);
        return piso;
    }

    public PisoModel buscarPisoPorNumero(Integer numero) {
        return pisos.stream()
            .filter(p -> p.getNumero().equals(numero))
            .findFirst()
            .orElseThrow(PisoException::noEncontrado);
    }
}
