package com.condominios.sgc.domain.model;

import java.time.LocalDateTime;

import com.condominios.sgc.domain.auxiliar.MetodoEntrada;
import com.condominios.sgc.domain.auxiliar.TipoHabitante;
import com.condominios.sgc.domain.exception.LogAccesoVehicularException;

public class LogAccesoVehicularModel {
    private Long id;
    private String placa;
    private TipoHabitante ocupante;
    private String datosInquilino;
    private MetodoEntrada metodo;
    private LocalDateTime fechaEntrada;
    private LocalDateTime fechaSalida;
    private Long vehiculoId;
    private Long estacionamientoId;

    public LogAccesoVehicularModel(Long id, String placa, TipoHabitante ocupante, String datosInquilino,
            MetodoEntrada metodo, Long vehiculoId, Long estacionamientoId) {
        validarDatos(placa, ocupante, metodo);
        this.id = id;
        this.placa = placa;
        this.ocupante = ocupante;
        this.datosInquilino = datosInquilino;
        this.metodo = metodo;
        this.vehiculoId = vehiculoId;
        this.estacionamientoId = estacionamientoId;
        this.fechaEntrada = LocalDateTime.now();
    }

    private void validarDatos(String placa, TipoHabitante ocupante, MetodoEntrada metodo) {
        if (placa == null || placa.trim().isEmpty()) {
            throw LogAccesoVehicularException.placaObligatoria();
        }
        if (ocupante == null) {
            throw LogAccesoVehicularException.ocupanteObligatorio();
        }
        if (metodo == null) {
            throw LogAccesoVehicularException.metodoObligatorio();
        }
    }

    public Long getId() { return id; }
    public String getPlaca() { return placa; }
    public TipoHabitante getOcupante() { return ocupante; }
    public String getDatosInquilino() { return datosInquilino; }
    public MetodoEntrada getMetodo() { return metodo; }
    public LocalDateTime getFechaEntrada() { return fechaEntrada; }
    public LocalDateTime getFechaSalida() { return fechaSalida; }
    public Long getVehiculoId() { return vehiculoId; }
    public Long getEstacionamientoId() { return estacionamientoId; }

    public void registrarSalida() {
        if (this.fechaSalida != null) {
            throw LogAccesoVehicularException.salidaYaRegistrada();
        }
        this.fechaSalida = LocalDateTime.now();
    }
}
