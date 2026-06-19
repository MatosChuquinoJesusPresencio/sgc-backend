package com.condominios.sgc.domain.model;

import java.time.Instant;

import com.condominios.sgc.domain.type.MetodoEntrada;
import com.condominios.sgc.domain.type.TipoHabitante;
import com.condominios.sgc.domain.shared.exception.LogAccesoVehicularException;
import com.condominios.sgc.domain.shared.value_objects.PlacaVehiculo;

import static com.condominios.sgc.domain.util.ValidacionUtil.*;

public class LogAccesoVehicularModel {
    private Long id;
    private PlacaVehiculo placa;
    private TipoHabitante ocupante;
    private String datosInquilino;
    private MetodoEntrada metodo;
    private Instant fechaEntrada;
    private Instant fechaSalida;
    private Long idVehiculo;
    private Long idEstacionamiento;

    public LogAccesoVehicularModel(Long id, PlacaVehiculo placa, TipoHabitante ocupante,
            String datosInquilino, MetodoEntrada metodo, Instant fechaEntrada,
            Instant fechaSalida, Long idVehiculo, Long idEstacionamiento) {
        this.id = id;
        this.placa = placa;
        this.ocupante = ocupante;
        this.datosInquilino = datosInquilino;
        this.metodo = metodo;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.idVehiculo = idVehiculo;
        this.idEstacionamiento = idEstacionamiento;
    }

    public LogAccesoVehicularModel(String placa, TipoHabitante ocupante,
            String datosInquilino, MetodoEntrada metodo, Long idVehiculo, Long idEstacionamiento) {
        this.id = null;
        this.placa = new PlacaVehiculo(placa);
        this.ocupante = noNulo(ocupante, LogAccesoVehicularException::ocupanteRequerido);
        this.datosInquilino = datosInquilino;
        this.metodo = noNulo(metodo, LogAccesoVehicularException::metodoRequerido);
        this.fechaEntrada = Instant.now();
        this.fechaSalida = null;
        this.idVehiculo = noNulo(idVehiculo, LogAccesoVehicularException::vehiculoRequerido);
        this.idEstacionamiento = noNulo(idEstacionamiento, LogAccesoVehicularException::estacionamientoRequerido);
    }

    public Long getId() { return id; }
    public PlacaVehiculo getPlaca() { return placa; }
    public TipoHabitante getOcupante() { return ocupante; }
    public String getDatosInquilino() { return datosInquilino; }
    public MetodoEntrada getMetodo() { return metodo; }
    public Instant getFechaEntrada() { return fechaEntrada; }
    public Instant getFechaSalida() { return fechaSalida; }
    public Long getIdVehiculo() { return idVehiculo; }
    public Long getIdEstacionamiento() { return idEstacionamiento; }

    public void registrarSalida() {
        if (this.fechaSalida != null)
            throw LogAccesoVehicularException.salidaYaRegistrada();
        this.fechaSalida = Instant.now();
    }
}
