package com.condominios.sgc.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.condominios.sgc.domain.exception.PisoException;
import com.condominios.sgc.domain.exception.TorreException;

public class TorreModel {
    private Long id;
    private String nombre;
    private List<Long> pisoIds;

    public TorreModel(Long id, String nombre, List<Long> pisoIds) {
        this.id = id;
        validarYAsignarNombre(nombre);
        this.pisoIds = pisoIds != null ? new ArrayList<>(pisoIds) : new ArrayList<>();
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public List<Long> getPisoIds() { return pisoIds; }

    public void agregarPiso(Long pisoId) {
        if (pisoId == null) {
            throw TorreException.pisoIdObligatorio();
        }
        if (contienePisoPorId(pisoId)) {
            throw PisoException.pisoYaExistePorId(pisoId);
        }
        pisoIds.add(pisoId);
    }

    public boolean contienePisoPorId(Long pisoId) {
        return pisoIds.stream().anyMatch(p -> Objects.equals(p, pisoId));
    }

    public void eliminarPisoPorId(Long pisoId) {
        if (pisoId == null) {
            throw TorreException.pisoIdObligatorio();
        }
        if (!contienePisoPorId(pisoId)) {
            throw PisoException.pisoNoExistePorId(pisoId);
        }
        pisoIds.removeIf(p -> Objects.equals(p, pisoId));
    }

    public void actualizarNombre(String nombre) {
        validarYAsignarNombre(nombre);
    }

    private void validarYAsignarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw TorreException.nombreObligatorio();
        }
        this.nombre = nombre;
    }
}
