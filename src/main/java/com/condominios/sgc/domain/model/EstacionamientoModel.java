package com.condominios.sgc.domain.model;

import static com.condominios.sgc.domain.util.ValidacionUtil.*;

import com.condominios.sgc.domain.auxiliar.TipoVehiculo;
import com.condominios.sgc.domain.exception.EstacionamientoException;

public class EstacionamientoModel {
    private Long id;
    private Integer numero;
    private TipoVehiculo tipoVehiculo;
    private Integer capacidadMaxima;
    private Integer cantidadActual;
    private Boolean disponible;
    private Long apartamentoId;
    private Long condominioId;

    public EstacionamientoModel(Integer numero, Long condominioId) {
        validarYAsignarDatos(numero, condominioId);
        this.cantidadActual = 0;
        this.disponible = true;
    }

    private void validarYAsignarDatos(Integer numero, Long condominioId) {
        this.numero = requerirNoNulo(numero, EstacionamientoException::numeroObligatorio);
        this.condominioId = requerirNoNulo(condominioId, EstacionamientoException::condominioIdObligatorio);
    }

    public EstacionamientoModel(Long id, Integer numero, TipoVehiculo tipoVehiculo, Integer capacidadMaxima, Integer cantidadActual, Boolean disponible, Long condominioId) {
        this.id = id;
        this.numero = numero;
        this.tipoVehiculo = tipoVehiculo;
        this.capacidadMaxima = capacidadMaxima;
        this.cantidadActual = cantidadActual;
        this.disponible = disponible;
        this.condominioId = condominioId;
    }

    public Long getId() { return id; }
    public Integer getNumero() { return numero; }
    public TipoVehiculo getTipoVehiculo() { return tipoVehiculo; }
    public Integer getCapacidadMaxima() { return capacidadMaxima; }
    public Integer getCantidadActual() { return cantidadActual; }
    public Boolean isDisponible() { return disponible; }
    public Long getApartamentoId() { return apartamentoId; }
    public Long getCondominioId() { return condominioId; }

    public void configurar(TipoVehiculo tipoVehiculo, Integer capacidadMaxima) {
        this.tipoVehiculo = requerirNoNulo(tipoVehiculo, EstacionamientoException::tipoVehiculoInvalido);
        this.capacidadMaxima = requerirPositivo(capacidadMaxima, EstacionamientoException::capacidadMaximaInvalida);
    }

    public void asignarAApartamento(Long apartamentoId) {
        this.apartamentoId = requerirNoNulo(apartamentoId, EstacionamientoException::apartamentoIdObligatorio);
    }

    public void incrementarOcupacion() {
        this.cantidadActual++;
        if (capacidadMaxima != null && cantidadActual >= capacidadMaxima) {
            this.disponible = false;
        }
    }

    public void decrementarOcupacion() {
        if (cantidadActual > 0) this.cantidadActual--;
        this.disponible = true;
    }

    public boolean hayEspacio() {
        return capacidadMaxima == null || cantidadActual < capacidadMaxima;
    }
}
