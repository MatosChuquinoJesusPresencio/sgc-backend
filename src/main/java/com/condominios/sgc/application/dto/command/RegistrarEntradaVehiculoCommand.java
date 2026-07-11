package com.condominios.sgc.application.dto.command;

import com.condominios.sgc.domain.type.MetodoEntrada;
import com.condominios.sgc.domain.type.TipoHabitante;

public record RegistrarEntradaVehiculoCommand(
    String placa,
    MetodoEntrada metodo,
    TipoHabitante ocupante,
    String datosInquilino,
    Long idEstacionamiento
) {
}
