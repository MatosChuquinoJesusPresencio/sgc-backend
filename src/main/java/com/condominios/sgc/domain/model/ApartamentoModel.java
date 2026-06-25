package com.condominios.sgc.domain.model;

import java.math.BigDecimal;

import com.condominios.sgc.domain.shared.exception.ApartamentoException;
import static com.condominios.sgc.domain.util.ValidacionUtil.*;

public class ApartamentoModel {
    private Long id;
    private Integer numero;
    private Boolean derechoEstacionamiento;
    private BigDecimal metraje;
    private Long idPropietario;

    public ApartamentoModel(Long id, Integer numero, Boolean derechoEstacionamiento,
            BigDecimal metraje, Long idPropietario) {
        this.id = id;
        this.numero = numero;
        this.derechoEstacionamiento = derechoEstacionamiento;
        this.metraje = metraje;
        this.idPropietario = idPropietario;
    }

    public ApartamentoModel(Integer numero, BigDecimal metraje) {
        this.id = null;
        this.numero = positivo(numero, ApartamentoException::numeroRequerido);
        this.derechoEstacionamiento = false;
        this.metraje = noNulo(metraje, ApartamentoException::metrajeRequerido);
        this.idPropietario = null;
    }

    public Long getId() { return id; }
    public Integer getNumero() { return numero; }
    public Boolean getDerechoEstacionamiento() { return derechoEstacionamiento; }
    public BigDecimal getMetraje() { return metraje;}
    public Long getIdPropietario() { return idPropietario; }

    public void actualizar(Integer numero, BigDecimal metraje) {
        this.numero = positivo(numero, ApartamentoException::numeroRequerido);
        this.metraje = noNulo(metraje, ApartamentoException::metrajeRequerido);
    }

    public void asignarPropietario(Long idPropietario) {
        this.idPropietario = noNulo(idPropietario, ApartamentoException::propietarioRequerido);
    }

    public void desasignarPropietario() {
        this.idPropietario = null;
    }

    public void darDerechoEstacionamiento() {
        this.derechoEstacionamiento = true;
    }

    public void quitarDerechoEstacionamiento() {
        this.derechoEstacionamiento = false;
    }
}
