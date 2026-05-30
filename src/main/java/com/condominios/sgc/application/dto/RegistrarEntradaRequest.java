package com.condominios.sgc.application.dto;

import com.condominios.sgc.domain.auxiliar.MetodoEntrada;
import com.condominios.sgc.domain.auxiliar.TipoHabitante;

public record RegistrarEntradaRequest(
    String placa,
    TipoHabitante ocupante,
    String datosInquilino,
    MetodoEntrada metodo,
    Long vehiculoId,
    Long estacionamientoId
) {}
