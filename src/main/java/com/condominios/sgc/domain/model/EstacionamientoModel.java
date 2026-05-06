package com.condominios.sgc.domain.model;

import com.condominios.sgc.domain.auxiliar.TipoVehiculo;
import com.condominios.sgc.domain.exception.EstacionamientoException;

public class EstacionamientoModel {
    private Long id;
    private Integer numero;
    private Integer cantidadVehiculosMax;
    private TipoVehiculo tipoVehiculo;
    private Boolean disponible;
    private Long apartamentoId;
    private Long condominioId;
    private Long vehiculoAsignadoId;

    public EstacionamientoModel(Long id, Integer numero, Integer cantidadVehiculosMax, TipoVehiculo tipoVehiculo, Long condominioId) {
        validarYAsignarDatos(numero, cantidadVehiculosMax, tipoVehiculo, condominioId);
        this.id = id;
        this.disponible = true;
    }

    private void validarYAsignarDatos(Integer numero, Integer cantidadVehiculosMax, TipoVehiculo tipoVehiculo, Long condominioId) {
        if (numero == null) {
            throw EstacionamientoException.numeroObligatorio();
        }
        this.numero = numero;

        if (cantidadVehiculosMax == null || cantidadVehiculosMax <= 0) {
            throw EstacionamientoException.cantidadVehiculosInvalida();
        }
        this.cantidadVehiculosMax = cantidadVehiculosMax;

        if (tipoVehiculo == null) {
            throw EstacionamientoException.tipoVehiculoInvalido();
        }
        this.tipoVehiculo = tipoVehiculo;

        if (condominioId == null) {
            throw EstacionamientoException.condominioIdObligatorio();
        }
        this.condominioId = condominioId;
    }

    public Long getId() { return id; }
    public Integer getNumero() { return numero; }
    public Integer getCantidadVehiculosMax() { return cantidadVehiculosMax; }
    public TipoVehiculo getTipoVehiculo() { return tipoVehiculo; }
    public Boolean isDisponible() { return disponible; }
    public Long getApartamentoId() { return apartamentoId; }
    public Long getCondominioId() { return condominioId; }
    public Long getVehiculoAsignadoId() { return vehiculoAsignadoId; }

    public void asignarAApartamento(Long apartamentoId) {
        if (apartamentoId == null) {
            throw EstacionamientoException.apartamentoIdObligatorio();
        }
        this.apartamentoId = apartamentoId;
    }

    public void ocuparConVehiculo(Long vehiculoId) {
        if (!this.disponible) {
            throw EstacionamientoException.estacionamientoOcupado();
        }
        this.vehiculoAsignadoId = vehiculoId;
        this.disponible = false;
    }

    public void liberar() {
        this.vehiculoAsignadoId = null;
        this.disponible = true;
    }
}
