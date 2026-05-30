package com.condominios.sgc.domain.model;

import static com.condominios.sgc.domain.util.ValidacionUtil.*;

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
        this.id = id;
        asignarDatos(placa, ocupante, metodo);
        this.datosInquilino = datosInquilino;
        this.vehiculoId = vehiculoId;
        this.estacionamientoId = estacionamientoId;
        this.fechaEntrada = LocalDateTime.now();
    }

    public LogAccesoVehicularModel(String placa, TipoHabitante ocupante, String datosInquilino,
            MetodoEntrada metodo, Long vehiculoId, Long estacionamientoId) {
        this(null, placa, ocupante, datosInquilino, metodo, vehiculoId, estacionamientoId);
    }

    private void asignarDatos(String placa, TipoHabitante ocupante, MetodoEntrada metodo) {
        this.placa = requerirNoVacio(placa, LogAccesoVehicularException::placaObligatoria);
        this.ocupante = requerirNoNulo(ocupante, LogAccesoVehicularException::ocupanteObligatorio);
        this.metodo = requerirNoNulo(metodo, LogAccesoVehicularException::metodoObligatorio);
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
        requerirQue(this.fechaSalida == null, LogAccesoVehicularException::salidaYaRegistrada);
        this.fechaSalida = LocalDateTime.now();
    }
}
