package com.condominios.sgc.domain.service;

import com.condominios.sgc.domain.type.MetodoEntrada;
import com.condominios.sgc.domain.type.TipoHabitante;
import com.condominios.sgc.domain.model.EstacionamientoModel;
import com.condominios.sgc.domain.model.LogAccesoVehicularModel;
import com.condominios.sgc.domain.model.VehiculoModel;

public class RegistroAccesoService {

    public LogAccesoVehicularModel registrarEntrada(
            VehiculoModel vehiculo,
            EstacionamientoModel estacionamiento,
            String placa,
            TipoHabitante ocupante,
            String datosInquilino,
            MetodoEntrada metodo) {
        if (estacionamiento != null) {
            estacionamiento.incrementarOcupacion();
        }
        var idEstacionamiento = estacionamiento != null ? estacionamiento.getId() : null;
        return new LogAccesoVehicularModel(
            placa, ocupante, datosInquilino, metodo,
            vehiculo.getId(), idEstacionamiento);
    }

    public void registrarSalida(
            LogAccesoVehicularModel log,
            EstacionamientoModel estacionamiento) {
        log.registrarSalida();
        if (estacionamiento != null) {
            estacionamiento.decrementarOcupacion();
        }
    }
}
