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

    public EstacionamientoModel(
        Long id,
        Integer numero,
        TipoVehiculo tipoVehiculo,
        Integer capacidadMaxima,
        Integer cantidadActual,
        Boolean disponible,
        Long condominioId,
        Long apartamentoId
    ) {
        this(numero, capacidadMaxima, cantidadActual, disponible, condominioId);
        this.id = id;
        this.apartamentoId = apartamentoId;
        this.tipoVehiculo = tipoVehiculo;
    }

    public EstacionamientoModel(
        Integer numero, 
        Integer capacidadMaxima, 
        Integer cantidadActual, 
        Boolean disponible, 
        Long condominioId
    ) {
        validarYAsignarDatos(numero, capacidadMaxima, cantidadActual, disponible, condominioId);
    }

    private void validarYAsignarDatos(Integer numero, Integer capacidadMaxima, Integer cantidadActual, Boolean disponible, Long condominioId) {
        this.numero = requerirNoNulo(numero, EstacionamientoException::numeroObligatorio);
        this.capacidadMaxima = requerirPositivo(capacidadMaxima, EstacionamientoException::capacidadMaximaInvalida);
        this.cantidadActual = requerirPositivo(cantidadActual, EstacionamientoException::cantidadActualInvalida);
        this.disponible = requerirNoNulo(disponible, EstacionamientoException::estadoDisponibleObligatorio);
        this.condominioId = requerirNoNulo(condominioId, EstacionamientoException::condominioIdObligatorio);
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

    public void actualizarDatos(Integer numero) {
        validarYAsignarDatos(numero, this.capacidadMaxima, this.cantidadActual, this.disponible, this.condominioId);
    }
}
