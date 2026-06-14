package com.condominios.sgc.application.dto.response;

import java.time.Instant;

import com.condominios.sgc.domain.auxiliar.MetodoEntrada;
import com.condominios.sgc.domain.auxiliar.TipoHabitante;
import com.condominios.sgc.domain.model.LogAccesoVehicularModel;

public record LogAccesoVehicularResponse(
    Long id,
    String placa,
    TipoHabitante ocupante,
    String datosInquilino,
    MetodoEntrada metodo,
    Instant fechaEntrada,
    Instant fechaSalida,
    Long idVehiculo,
    Long idEstacionamiento
) {
    public static LogAccesoVehicularResponse desdeModelo(LogAccesoVehicularModel model) {
        return new LogAccesoVehicularResponse(
            model.getId(), model.getPlaca(), model.getOcupante(),
            model.getDatosInquilino(), model.getMetodo(),
            model.getFechaEntrada(), model.getFechaSalida(),
            model.getIdVehiculo(), model.getIdEstacionamiento());
    }
}
