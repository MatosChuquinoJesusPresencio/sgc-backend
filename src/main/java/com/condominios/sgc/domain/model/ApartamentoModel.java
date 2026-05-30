package com.condominios.sgc.domain.model;

import static com.condominios.sgc.domain.util.ValidacionUtil.*;

import java.math.BigDecimal;

import com.condominios.sgc.domain.exception.ApartamentoException;

public class ApartamentoModel {

    private Long id;
    private Integer numero;
    private Boolean derechoEstacionamiento;
    private BigDecimal metraje;
    private String propietarioId;
    private Long pisoId;

    public ApartamentoModel(
        Long id,
        Integer numero,
        Boolean derechoEstacionamiento,
        BigDecimal metraje,
        String propietarioId,
        Long pisoId
    ) {
        this.id = id;
        asignarDatos(numero, derechoEstacionamiento, metraje, pisoId);
        this.propietarioId = propietarioId;
    }

    public ApartamentoModel(
        Integer numero,
        Boolean derechoEstacionamiento,
        BigDecimal metraje,
        Long pisoId
    ) {
        this(null, numero, derechoEstacionamiento, metraje, null, pisoId);
    }

    private void asignarDatos(Integer numero, Boolean derechoEstacionamiento, BigDecimal metraje, Long pisoId) {
        this.numero = requerirPositivo(numero, ApartamentoException::numeroInvalido);
        this.metraje = requerirPositivo(metraje, ApartamentoException::metrajeInvalido);
        this.derechoEstacionamiento = requerirNoNulo(derechoEstacionamiento, ApartamentoException::derechoEstacionamientoInvalido);
        this.pisoId = requerirNoNulo(pisoId, ApartamentoException::pisoIdObligatorio);
    }

    public Long getId() { return id; }
    public Integer getNumero() { return numero; }
    public Boolean getDerechoEstacionamiento() { return derechoEstacionamiento; }
    public BigDecimal getMetraje() { return metraje; }
    public String getPropietarioId() { return propietarioId; }
    public Long getPisoId() { return pisoId; }

    public void asignarPropietario(String propietarioId) {
        this.propietarioId = requerirNoNulo(propietarioId, ApartamentoException::propietarioIdObligatorio);
    }

    public void removerPropietario() {
        this.propietarioId = null;
    }

    public void actualizar(
        Integer numero,
        BigDecimal metraje,
        Boolean derechoEstacionamiento,
        Long pisoId
    ) {
        asignarDatos(numero, derechoEstacionamiento, metraje, pisoId);
    }
}
