package com.condominios.sgc.web.dto;

import java.time.LocalDateTime;

import com.condominios.sgc.domain.auxiliar.MetodoEntrada;
import com.condominios.sgc.domain.auxiliar.TipoHabitante;
import com.condominios.sgc.domain.model.LogAccesoVehicularModel;

public record LogAccesoVehicularResponse(
    Long id,
    String placa,
    TipoHabitante ocupante,
    String datosInquilino,
    MetodoEntrada metodo,
    LocalDateTime fechaEntrada,
    LocalDateTime fechaSalida,
    Long vehiculoId,
    Long estacionamientoId
) {
    public static LogAccesoVehicularResponse fromModel(LogAccesoVehicularModel model) {
        return new LogAccesoVehicularResponse(
            model.getId(),
            model.getPlaca(),
            model.getOcupante(),
            model.getDatosInquilino(),
            model.getMetodo(),
            model.getFechaEntrada(),
            model.getFechaSalida(),
            model.getVehiculoId(),
            model.getEstacionamientoId()
        );
    }
}
