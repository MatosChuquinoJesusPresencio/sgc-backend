package com.condominios.sgc.domain.model;

import java.math.BigDecimal;

import com.condominios.sgc.domain.exception.ApartamentoException;

public class ApartamentoModel {

    private Long id;
    private Integer numero;
    private Boolean derechoEstacionamiento;
    private BigDecimal metraje;
    private String usuarioPropietarioId;
    private Long pisoId;

    public ApartamentoModel(
        Long id,
        Integer numero,
        Boolean derechoEstacionamiento,
        BigDecimal metraje,
        Long pisoId
    ) {
        validarYAsignarDatos(numero, metraje, pisoId);
        this.id = id;
        this.derechoEstacionamiento = derechoEstacionamiento;
    }

    public Long getId() {
        return id;
    }

    public Integer getNumero() {
        return numero;
    }

    public Boolean getDerechoEstacionamiento() {
        return derechoEstacionamiento;
    }

    public BigDecimal getMetraje() {
        return metraje;
    }

    public String getUsuarioPropietarioId() {
        return usuarioPropietarioId;
    }

    public Long getPisoId() {
        return pisoId;
    }

    private void validarYAsignarDatos(Integer numero, BigDecimal metraje, Long pisoId) {
        if (numero == null || numero <= 0) {
            throw ApartamentoException.numeroInvalido();
        }
        this.numero = numero;

        if (metraje == null || metraje.compareTo(BigDecimal.ZERO) <= 0) {
            throw ApartamentoException.metrajeInvalido();
        }
        this.metraje = metraje;

        if (pisoId == null) {
            throw ApartamentoException.pisoIdObligatorio();
        }
        this.pisoId = pisoId;
    }

    public void asignarPropietario(String usuarioId) {
        if (usuarioId == null) {
            throw ApartamentoException.datosObligatorios();
        }
        this.usuarioPropietarioId = usuarioId;
    }

    public void removerPropietario() {
        this.usuarioPropietarioId = null;
    }
}
