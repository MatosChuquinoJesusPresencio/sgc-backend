package com.condominios.sgc.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.condominios.sgc.domain.exception.ApartamentoException;
import com.condominios.sgc.domain.exception.PisoException;

public class PisoModel {
    private Long id;
    private Integer numero;
    private List<Long> apartamentoIds;

    public PisoModel(Long id, Integer numero, List<Long> apartamentoIds) {
        this.id = id;
        validarYAsignarNumero(numero);
        this.apartamentoIds = apartamentoIds != null ? new ArrayList<>(apartamentoIds) : new ArrayList<>();
    }

    public Long getId() { return id; }
    public Integer getNumero() { return numero; }
    public List<Long> getApartamentoIds() { return apartamentoIds; }

    public void agregarApartamento(Long apartamentoId) {
        if (apartamentoId == null) {
            throw PisoException.apartamentoIdObligatorio();
        }
        if (contieneApartamentoPorId(apartamentoId)) {
            throw ApartamentoException.apartamentoYaExistePorId(apartamentoId);
        }
        apartamentoIds.add(apartamentoId);
    }

    public boolean contieneApartamentoPorId(Long apartamentoId) {
        return apartamentoIds.stream().anyMatch(a -> Objects.equals(a, apartamentoId));
    }

    public void eliminarApartamentoPorId(Long apartamentoId) {
        if (apartamentoId == null) {
            throw PisoException.apartamentoIdObligatorio();
        }
        if (!contieneApartamentoPorId(apartamentoId)) {
            throw ApartamentoException.apartamentoNoExistePorId(apartamentoId);
        }
        apartamentoIds.removeIf(a -> Objects.equals(a, apartamentoId));
    }

    public void actualizarNumero(Integer numero) {
        validarYAsignarNumero(numero);
    }

    private void validarYAsignarNumero(Integer numero) {
        if (numero == null || numero <= 0) {
            throw PisoException.numeroInvalido();
        }
        this.numero = numero;
    }
}
