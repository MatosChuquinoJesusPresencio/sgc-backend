package com.condominios.sgc.domain.model;

import static com.condominios.sgc.domain.util.ValidacionUtil.*;

import java.math.BigDecimal;

import com.condominios.sgc.domain.exception.ApartamentoException;

public class ApartamentoModel {

    private Long id;
    private Integer numero;
    private Boolean derechoEstacionamiento;
    private BigDecimal metraje;
    private Long propietarioId;
    private Long pisoId;

    public ApartamentoModel(
        Long id,
        Integer numero,
        Boolean derechoEstacionamiento,
        BigDecimal metraje,
        Long propietarioId,
        Long pisoId
    ) {
        this(numero, derechoEstacionamiento, metraje, pisoId);
        this.id = id;
        this.propietarioId = propietarioId;
    }

    public ApartamentoModel(
        Integer numero,
        Boolean derechoEstacionamiento,
        BigDecimal metraje,
        Long pisoId
    ) {
        validarYAsignarDatos(numero, derechoEstacionamiento, metraje, pisoId);
    }

    private void validarYAsignarDatos(Integer numero, Boolean derechoEstacionamiento, BigDecimal metraje, Long pisoId) {
        this.numero = requerirPositivo(numero, ApartamentoException::numeroInvalido);
        this.metraje = requerirPositivo(metraje, ApartamentoException::metrajeInvalido);
        this.derechoEstacionamiento = requerirNoNulo(derechoEstacionamiento, ApartamentoException::derechoEstacionamientoObligatorio);
        this.pisoId = requerirNoNulo(pisoId, ApartamentoException::pisoIdObligatorio);
    }

    public Long getId() { return id; }
    public Integer getNumero() { return numero; }
    public Boolean getDerechoEstacionamiento() { return derechoEstacionamiento; }
    public BigDecimal getMetraje() { return metraje; }
    public Long getPropietarioId() { return propietarioId; }
    public Long getPisoId() { return pisoId; }

    public void asignarPropietario(Long propietarioId) {
        this.propietarioId = requerirNoNulo(propietarioId, ApartamentoException::propietarioIdObligatorio);
    }

    public void removerPropietario() {
        this.propietarioId = null;
    }

    public void actualizarDatos(
        Integer numero,
        BigDecimal metraje,
        Boolean derechoEstacionamiento
    ) {
        validarYAsignarDatos(numero, derechoEstacionamiento, metraje, this.pisoId);
    }
}
