package com.condominios.sgc.application.dto.command;

import com.condominios.sgc.domain.auxiliar.MetodoEntrada;
import com.condominios.sgc.domain.auxiliar.TipoHabitante;

public record CrearLogAccesoVehicularCommand(
    String placa,
    TipoHabitante ocupante,
    String datosInquilino,
    MetodoEntrada metodo,
    Long idVehiculo,
    Long idEstacionamiento
) {}
