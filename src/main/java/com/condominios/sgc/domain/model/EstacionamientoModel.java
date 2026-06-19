package com.condominios.sgc.domain.model;

import com.condominios.sgc.domain.shared.exception.EstacionamientoException;

import static com.condominios.sgc.domain.util.ValidacionUtil.*;

import com.condominios.sgc.domain.type.TipoVehiculo;

public class EstacionamientoModel {
    private Long id;
    private Integer numero;
    private TipoVehiculo tipoVehiculo;
    private Integer capacidadMaxima;
    private Integer cantidadActual;
    private Boolean disponible;
    private Long idApartamento;
    private Long idCondominio;

    public EstacionamientoModel(Long id, Integer numero, TipoVehiculo tipoVehiculo, Integer capacidadMaxima,
            Integer cantidadActual, Boolean disponible, Long idApartamento, Long idCondominio) {
        this.id = id;
        this.numero = numero;
        this.tipoVehiculo = tipoVehiculo;
        this.capacidadMaxima = capacidadMaxima;
        this.cantidadActual = cantidadActual;
        this.disponible = disponible;
        this.idApartamento = idApartamento;
        this.idCondominio = idCondominio;
    }

    public EstacionamientoModel(Integer numero, Long idCondominio) {
        this.id = null;
        this.numero = positivo(numero, EstacionamientoException::numeroRequerido);
        this.tipoVehiculo = null;
        this.capacidadMaxima = null;
        this.cantidadActual = 0;
        this.disponible = true;
        this.idApartamento = null;
        this.idCondominio = noNulo(idCondominio, EstacionamientoException::condominioRequerido);
    }

    public Long getId() { return id; }
    public Integer getNumero() { return numero; }
    public TipoVehiculo getTipoVehiculo() { return tipoVehiculo; }
    public Integer getCapacidadMaxima() { return capacidadMaxima; }
    public Integer getCantidadActual() { return cantidadActual; }
    public Boolean getDisponible() { return disponible; }
    public Long getIdApartamento() { return idApartamento; }
    public Long getIdCondominio() { return idCondominio; }

    public void configurar(TipoVehiculo tipoVehiculo, Integer capacidadMaxima) {
        this.tipoVehiculo = noNulo(tipoVehiculo, EstacionamientoException::tipoVehiculoRequerido);
        this.capacidadMaxima = positivo(capacidadMaxima, EstacionamientoException::capacidadRequerida);
    }

    public void reiniciar() {
        this.tipoVehiculo = null;
        this.capacidadMaxima = null;
    }

    public void asignarApartamento(Long idApartamento) {
        this.idApartamento = noNulo(idApartamento, EstacionamientoException::apartamentoRequerido);
    }

    public void desasignarApartamento() {
        this.idApartamento = null;
    }

    public void incrementarOcupacion() {
        if (!hayEspacio())
            throw EstacionamientoException.sinEspacio();
        this.cantidadActual++;
        if (this.capacidadMaxima != null && this.cantidadActual >= this.capacidadMaxima) {
            this.disponible = false;
        }
    }

    public void decrementarOcupacion() {
        if (this.cantidadActual > 0) {
            this.cantidadActual--;
        }
        this.disponible = this.capacidadMaxima == null || this.cantidadActual < this.capacidadMaxima;
    }

    public boolean hayEspacio() {
        return this.capacidadMaxima == null || this.cantidadActual < this.capacidadMaxima;
    }

    public void actualizarNumero(Integer numero) {
        this.numero = positivo(numero, EstacionamientoException::numeroRequerido);
    }
}
